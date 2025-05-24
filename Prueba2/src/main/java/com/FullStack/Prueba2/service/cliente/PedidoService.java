package com.FullStack.Prueba2.service.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FullStack.Prueba2.model.cliente.Cliente;
import com.FullStack.Prueba2.model.cliente.Pedido;
import com.FullStack.Prueba2.model.gestionInventario.Producto;
import com.FullStack.Prueba2.repository.cliente.PedidoRepository;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    //Listar
public String getAllPedidos() {
    StringBuilder output = new StringBuilder();

    for (Pedido pedido : pedidoRepository.findAll()) {
        output.append("ID Pedido: ").append(pedido.getIdPedido()).append("\n");

        if (pedido.getCliente() != null) {
            output.append("Cliente Asignado: ").append(pedido.getCliente().getNombreCliente()).append("\n");
        } else {
            output.append("Cliente Asignado: Sin cliente\n");
        }

        output.append("Estado: ").append(pedido.getEstado()).append("\n");

        if (pedido.getProductos() != null && !pedido.getProductos().isEmpty()) {
            output.append("Productos:\n");
            for (Producto producto : pedido.getProductos()) {
                output.append("- ").append(producto.getNombre()).append(" (ID: ")
                      .append(producto.getIdProducto()).append(")\n");
            }
        } else {
            output.append("Productos: Ninguno\n");
        }
        output.append("\n");
        output.append("------------------o------------------\n");
        output.append("\n");
    }

    return output.isEmpty() ? "No se encontraron pedidos" : output.toString();
}

    //Buscar
public String getPedidoById(Long id) {
    StringBuilder output = new StringBuilder();

    if (pedidoRepository.existsById(id)) {
        Pedido buscado = pedidoRepository.findById(id).get();

        output.append("\n");
        output.append("ID Pedido: ").append(buscado.getIdPedido()).append("\n");

        Cliente cliente = buscado.getCliente();
        if (cliente != null) {
            output.append("Cliente Asignado: ").append(cliente.getNombreCliente())
                  .append(" (ID: ").append(cliente.getIdCliente()).append(")\n");
        } else {
            output.append("Cliente Asignado: Sin asignar\n");
        }

        output.append("Estado: ").append(buscado.getEstado()).append("\n");

        if (buscado.getProductos() != null && !buscado.getProductos().isEmpty()) {
            output.append("Productos:\n");
            for (Producto producto : buscado.getProductos()) {
                output.append("- ").append(producto.getNombre())
                      .append(" (ID: ").append(producto.getIdProducto()).append(")\n");
            }
        } else {
            output.append("Productos: Ninguno\n");
        }

        output.append("\n");
        output.append("------------------o------------------\n");
        output.append("\n");

        return output.toString();
    } else {
        return "No se encontraron pedidos con esa ID";
    }
}

    //Agregar
public String addPedido(Pedido pedido) {
    pedidoRepository.save(pedido);
    return "Pedido agregado correctamente";
}

    //Eliminar
    public String deletePedido(Long id){
        if(pedidoRepository.existsById(id)){
            pedidoRepository.deleteById(id);
            return "Pedido eliminado correctamente";
        }else{
            return "No se encontraron pedidos con esa ID";
        }
    }

    //Actualizar
    public String updatePedido(Long id, Pedido pedido){
        if(pedidoRepository.existsById(id)){
            Pedido buscado = pedidoRepository.findById(id).get();
            buscado.setCliente(pedido.getCliente());
            buscado.setEstado(pedido.getEstado());
            buscado.setProductos(pedido.getProductos());
            pedidoRepository.save(buscado);
            return "Pedido actualizado correctamente";
        }else{
            return "No se encontraron pedidos con esa ID";
        }
    }
}
