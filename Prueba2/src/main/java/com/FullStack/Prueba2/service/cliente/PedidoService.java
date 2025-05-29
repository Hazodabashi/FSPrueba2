package com.FullStack.Prueba2.service.cliente;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.FullStack.Prueba2.model.cliente.Pedido;
import com.FullStack.Prueba2.repository.cliente.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    //Listar
    public List<Pedido> getAllPedidos(){
        return pedidoRepository.findAll();
    }

    //Buscar x ID
    public Pedido getPedidoById(Long id) {
    return pedidoRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con ID: " + id));
    }

    //Agregar
    public Pedido addPedido(Pedido pedido) {
    return pedidoRepository.save(pedido);
    }


    //Eliminar
    public void eliminarPedido(Long id) {
    if (!pedidoRepository.existsById(id)) {
        throw new EntityNotFoundException("Pedido no encontrado con ID: " + id);
    }
    pedidoRepository.deleteById(id);
    }


    //Actualizar
    public Pedido actualizarPedido(Long id, Pedido pedidoActualizado) {
    Pedido pedidoExistente = pedidoRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con ID: " + id));

    pedidoExistente.setEstado(pedidoActualizado.getEstado());
    pedidoExistente.setCliente(pedidoActualizado.getCliente());
    pedidoExistente.setProductos(pedidoActualizado.getProductos());

    return pedidoRepository.save(pedidoExistente);
    }

}
