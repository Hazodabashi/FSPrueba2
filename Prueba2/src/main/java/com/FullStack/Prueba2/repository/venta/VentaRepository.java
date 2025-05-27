package com.FullStack.Prueba2.repository.venta;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FullStack.Prueba2.model.venta.Venta;

public interface VentaRepository extends JpaRepository<Venta,Long> {
    List<Venta> findByClienteIdCliente(Long clienteId);
}
