package com.jldev.apinf.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jldev.apinf.entity.Empresa;
import com.jldev.apinf.entity.NotaEntrada;
import com.jldev.apinf.exception.SistemaException;
import com.jldev.apinf.repository.EmpresaRepository;
import com.jldev.apinf.util.ArquivoUtil;

import br.com.swconsultoria.certificado.Certificado;
import br.com.swconsultoria.certificado.CertificadoService;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.Nfe;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.enuns.ConsultaDFeEnum;
import br.com.swconsultoria.nfe.dom.enuns.EstadosEnum;
import br.com.swconsultoria.nfe.dom.enuns.PessoaEnum;
import br.com.swconsultoria.nfe.dom.enuns.StatusEnum;
import br.com.swconsultoria.nfe.exception.NfeException;
import br.com.swconsultoria.nfe.schema.resnfe.ResNFe;
import br.com.swconsultoria.nfe.schema.retdistdfeint.RetDistDFeInt;
import br.com.swconsultoria.nfe.schema_4.consReciNFe.TNfeProc;
import br.com.swconsultoria.nfe.util.ObjetoUtil;
import br.com.swconsultoria.nfe.util.XmlNfeUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DistribuicaoService {
	private final EmpresaRepository empresaRepository;
	private final NotaEntradaService notaEntradaService;
	
	
	
	
	public DistribuicaoService(EmpresaRepository empresaRepository, NotaEntradaService notaEntradaService) {
		this.empresaRepository = empresaRepository;
		this.notaEntradaService = notaEntradaService;
	}

	public void consultaNotas(Empresa empresa) throws CertificadoException, NfeException, IOException, Throwable {
		ConfiguracoesNfe configuracao = criarConfiguracao(empresa);
		List<String> listaNotasManifestar = new ArrayList<>();
		List<NotaEntrada> listaNotasSalvar = new ArrayList<>();

		boolean existeMaisNotas = true;
		while (existeMaisNotas) {

			RetDistDFeInt retorno = Nfe.distribuicaoDfe(configuracao, PessoaEnum.FISICA, empresa.getCnpj(),
					ConsultaDFeEnum.NSU, ObjetoUtil.verifica(empresa.getNsu()).orElse("000000000000000"));

			if (!retorno.getCStat().equals(StatusEnum.DOC_LOCALIZADO_PARA_DESTINATARIO)) {

				if (retorno.getCStat().equals(StatusEnum.CONSUMO_INDEVIDO)) {

					break;

				} else {
					throw new SistemaException(
							"Erro ao pesquisar notas: " + retorno.getCStat() + "-" + retorno.getXMotivo());

				}
			}

			for (RetDistDFeInt.LoteDistDFeInt.DocZip doc : retorno.getLoteDistDFeInt().getDocZip()) {

				String xml = XmlNfeUtil.gZipToXml(doc.getValue());

				log.info("Xml: " + xml);
				log.info("Schema" + doc.getSchema());
				log.info("Nsu" + doc.getNSU());
				switch (doc.getSchema()) {
				case "resNFe_v1.01.xsd":
					ResNFe resNFe = XmlNfeUtil.xmlToObject(xml, ResNFe.class);
					String chave = resNFe.getChNFe();
					listaNotasManifestar.add(chave);
					break;

				case "procNFe_v4.00.xsd":
					TNfeProc nfe = XmlNfeUtil.xmlToObject(xml, TNfeProc.class);
					NotaEntrada notaEntrada = new NotaEntrada();
					notaEntrada.setChave(nfe.getNFe().getInfNFe().getId().substring(3));
					notaEntrada.setEmpresa(empresa);
					notaEntrada.setSchema(doc.getSchema());
					notaEntrada.setCnpjEmitente(nfe.getNFe().getInfNFe().getEmit().getCNPJ());
					notaEntrada.setNomeEmitente(nfe.getNFe().getInfNFe().getEmit().getXNome());
					notaEntrada.setValor(new BigDecimal(nfe.getNFe().getInfNFe().getTotal().getICMSTot().getVNF()));
					notaEntrada.setXml(ArquivoUtil.compactaXml(xml));
					listaNotasSalvar.add(notaEntrada);
				default:
					break;

				}

			}

			existeMaisNotas = !retorno.getUltNSU().equals(retorno.getMaxNSU());
			empresa.setNsu(retorno.getUltNSU());

		}
		
		empresaRepository.save(empresa);
		notaEntradaService.salvar(listaNotasSalvar);
		
	}

	public ConfiguracoesNfe criarConfiguracao(Empresa empresa) throws CertificadoException {
		Certificado certificado = CertificadoService.certificadoPfxBytes(empresa.getCertificado(),
				empresa.getSenhaCertificado());
		return ConfiguracoesNfe.criarConfiguracoes(EstadosEnum.valueOf(empresa.getUf()), empresa.getAmbiente(),
				certificado, "D://Projetos/Java_NFe/schemas");

	}
}
