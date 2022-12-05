package com.jldev.apinf.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "nota_entrada")
@SequenceGenerator(name = "NotaEntradaSeq", sequenceName = "SEQ_NOTA_ENTRADA", allocationSize = 1)
@Data
public class NotaEntrada {
	@Id
	@GeneratedValue(generator = "NotaEntradaSeq", strategy = GenerationType.SEQUENCE)
	private Long id;
	private String schema;
	private String chave;
	private String nomeEmitente;
	private String cnpjEmitente;
	private BigDecimal valor;
	private byte[] xml;

	@ManyToOne
	@JoinColumn(name = "empresa_id")
	private Empresa empresa;

}
