package com.jldev.apinf.entity;



import jakarta.persistence.Entity;
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
	private String razaoSocial;
	private byte[] certificado;
	private String senhaCertificado;
	private String nsu;
}
