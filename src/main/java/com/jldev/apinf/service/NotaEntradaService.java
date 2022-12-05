package com.jldev.apinf.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jldev.apinf.entity.NotaEntrada;
import com.jldev.apinf.exception.SistemaException;
import com.jldev.apinf.repository.NotaEntradaRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotaEntradaService {

	private final NotaEntradaRepository repository;

	public NotaEntradaService(NotaEntradaRepository repository) {
		this.repository = repository;
	}

	private NotaEntrada salvar(NotaEntrada NotaEntrada) {
		validar(NotaEntrada);
		log.info("Salvando NotaEntrada");
		return repository.save(NotaEntrada);

	}

	private void deletar(Long idNotaEntrada) {
		
		repository.deleteById(idNotaEntrada);
		
	}
	
	
	private List<NotaEntrada>listarTudo(){
		return repository.findAll();
		
	}
	
	
	
	private void validar(NotaEntrada NotaEntrada) {
		// TODO Auto-generated method stub
		Optional.ofNullable(NotaEntrada.getChave()).orElseThrow(() -> new SistemaException("Campo Cnpj Obrigatório"));
	}
		
}
