package com.jldev.apinf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jldev.apinf.entity.NotaEntrada;
@Repository
public interface NotaEntradaRepository extends JpaRepository<NotaEntrada, Long> {

}
