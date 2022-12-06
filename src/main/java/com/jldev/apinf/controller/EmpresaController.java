package com.jldev.apinf.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jldev.apinf.entity.Empresa;
import com.jldev.apinf.service.EmpresaService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/empresa")
@Slf4j
public class EmpresaController {

	private final EmpresaService empresaService;

	public EmpresaController(EmpresaService empresaService) {

		this.empresaService = empresaService;
	}

	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody Empresa empresa) {
		try {
			empresaService.salvar(empresa);
			return ResponseEntity.ok(empresa);

		} catch (Exception e) {
			log.error("Error ao salvar Empresa", e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}
	
	@GetMapping
	public ResponseEntity<?> listarTodos() {
		try {
			
			return ResponseEntity.ok(empresaService.listarTudo());

		} catch (Exception e) {
			log.error("Error ao listar Empresas", e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}
	
	
	@GetMapping(value="{id}")
	public ResponseEntity<?> listarPorId(@PathVariable Long idEmpresa) {
		try {
			
			return ResponseEntity.ok(empresaService.listarPorId(idEmpresa));

		} catch (Exception e) {
			log.error("Error ao obter Empresa", e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}
	
	
	
	@DeleteMapping(value="{id}")
	public ResponseEntity<?> deletar(@PathVariable("id") Long idEmpresa) {
		try {
			empresaService.deletar(idEmpresa);
			return ResponseEntity.ok("Empresa deletada com Sucesso");

		} catch (Exception e) {
			log.error("Error ao deletar Empresa", e);
			return ResponseEntity.badRequest().body("Error ao deletar empresa: "+e.getMessage());
		}

	}
	
}
