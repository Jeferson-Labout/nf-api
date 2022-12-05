package com.jldev.apinf.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jldev.apinf.entity.Empresa;
import com.jldev.apinf.exception.SistemaException;
import com.jldev.apinf.repository.EmpresaRepository;

import br.com.swconsultoria.nfe.util.ObjetoUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmpresaService {

	private final EmpresaRepository repository;

	public EmpresaService(EmpresaRepository repository) {
		this.repository = repository;
	}

	public Empresa salvar(Empresa empresa) {
		validar(empresa);
		log.info("Salvando Empresa");
		return repository.save(empresa);

	}

	public void deletar(Long idEmpresa) {
		
		repository.deleteById(idEmpresa);
		
	}
	
	
	public List<Empresa>listarTudo(){
		return repository.findAll();
		
	}
	
public Empresa listarPorId(Long idEmpresa) {
		
		return repository.findById(idEmpresa)
				.orElseThrow(()-> new SistemaException("Empresa não encontrada com id:" + idEmpresa));
		
	}
	
	
	public void validar(Empresa empresa) {
		// TODO Auto-generated method stub
		ObjetoUtil.verifica(empresa.getCnpj()).orElseThrow(() -> new SistemaException("Campo Cnpj Obrigatório"));
		
		ObjetoUtil.verifica(empresa.getCertificado()).orElseThrow(() -> new SistemaException("Campo Certificado Obrigatório"));
	}
}
