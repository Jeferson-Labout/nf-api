package com.jldev.apinf.entity;





import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.swconsultoria.nfe.dom.enuns.AmbienteEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Entity
@SequenceGenerator(name = "NotaEntradaSeq", sequenceName = "SEQ_NOTA_ENTRADA", allocationSize = 1)
@Data
public class Empresa {
	@Id	
	@GeneratedValue(generator = "NotaEntradaSeq", strategy = GenerationType.SEQUENCE)
	private Long id;
	private String cnpj;
	private String uf;
	private String razaoSocial;
	@Enumerated(EnumType.STRING)
	private AmbienteEnum ambiente;	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private byte[] certificado;
	private String senhaCertificado;
	private String nsu;
}
