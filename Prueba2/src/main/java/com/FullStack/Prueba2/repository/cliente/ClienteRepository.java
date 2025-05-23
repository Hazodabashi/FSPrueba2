package com.FullStack.Prueba2.repository.cliente;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FullStack.Prueba2.model.cliente.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {

}
