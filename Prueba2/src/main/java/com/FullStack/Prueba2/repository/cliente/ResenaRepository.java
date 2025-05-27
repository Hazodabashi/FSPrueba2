package com.FullStack.Prueba2.repository.cliente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FullStack.Prueba2.model.cliente.Resena;

public interface ResenaRepository extends JpaRepository<Resena,Long> {
    List<Resena> findByClienteIdCliente(Long clienteId);
}
