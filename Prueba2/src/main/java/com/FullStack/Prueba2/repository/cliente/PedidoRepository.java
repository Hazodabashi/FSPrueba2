package com.FullStack.Prueba2.repository.cliente;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FullStack.Prueba2.model.cliente.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {

}
