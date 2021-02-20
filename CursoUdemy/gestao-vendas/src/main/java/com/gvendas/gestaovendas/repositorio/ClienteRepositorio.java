package com.gvendas.gestaovendas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gvendas.gestaovendas.entidades.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {

	Cliente findByNome(String nome);
}
