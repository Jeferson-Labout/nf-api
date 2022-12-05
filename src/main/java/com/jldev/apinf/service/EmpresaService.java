package com.jldev.apinf.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jldev.apinf.entity.Empresa;
import com.jldev.apinf.repository.EmpresaRepository;

@Service
public class EmpresaService {
	
	private final EmpresaRepository repository;
	
	public EmpresaService(EmpresaRepository repository) {this.repository= repository;}

	private Empresa salvar(Empresa empresa) {
		validar(empresa);
		
		return repository.save(empresa);
		
	}

	private void validar(Empresa empresa) {
		// TODO Auto-generated method stub
		Optional.ofNullable(empresa.getCnpj()).orElseThrow(()-> {
			
		})
		
	}
}
