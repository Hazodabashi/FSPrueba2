package com.FullStack.Prueba2.repository.envio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FullStack.Prueba2.model.envio.Envio;

public interface EnvioRepository extends JpaRepository<Envio,Long> {
    List<Envio> findByClienteIdCliente(Long clienteId);
}
