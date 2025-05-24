package com.FullStack.Prueba2.service.envio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FullStack.Prueba2.model.cliente.Cliente;
import com.FullStack.Prueba2.model.cliente.Pedido;
import com.FullStack.Prueba2.model.envio.Envio;
import com.FullStack.Prueba2.model.gestionInventario.Producto;
import com.FullStack.Prueba2.repository.envio.EnvioRepository;

@Service
public class EnvioService {
    @Autowired
    private EnvioRepository envioRepository;

    //Listar
public String getAllEnvios() {
    StringBuilder output = new StringBuilder();

    for (Envio envio : envioRepository.findAll()) {
        output.append("ID Envío: ").append(envio.getIdEnvio()).append("\n");

        // Datos del pedido
        Pedido pedido = envio.getPedido();
        if (pedido != null) {
            output.append("Pedido ID: ").append(pedido.getIdPedido()).append("\n")
                  .append("Estado Pedido: ").append(pedido.getEstado()).append("\n");

            List<Producto> productos = pedido.getProductos();
            if (productos != null && !productos.isEmpty()) {
                output.append("Productos en el pedido:\n");
                for (Producto producto : productos) {
                    output.append("  - ID: ").append(producto.getIdProducto()).append("\n")
                          .append("    Nombre: ").append(producto.getNombre()).append("\n")
                          .append("    Categoría: ").append(producto.getCategoria()).append("\n")
                          .append("    Precio: $").append(producto.getPrecio()).append("\n")
                          .append("    Stock: ").append(producto.getStock()).append("\n");
                }
            } else {
                output.append("Productos en el pedido: Ninguno\n");
            }
        } else {
            output.append("Pedido: No asignado\n");
        }

        // Datos del cliente
        Cliente cliente = envio.getCliente();
        if (cliente != null) {
            output.append("Cliente ID: ").append(cliente.getIdCliente()).append("\n")
                  .append("Nombre: ").append(cliente.getNombreCliente()).append("\n")
                  .append("Email: ").append(cliente.getEmailCliente()).append("\n")
                  .append("Dirección: ").append(cliente.getDireccionCliente()).append("\n")
                  .append("RUN: ").append(cliente.getRun()).append("\n");
        } else {
            output.append("Cliente: No asignado\n");
        }

        // Datos del envío
        output.append("Dirección Envío: ").append(envio.getDireccionEnvio()).append("\n")
              .append("Fecha Envío: ").append(envio.getFechaEnvio()).append("\n")
              .append("Estado Envío: ").append(envio.getEstadoEnvio()).append("\n")
              .append("--------------------------------------------------\n");
    }

    return output.length() == 0 ? "No se encontraron envíos" : output.toString();
}


    //Buscar
public String getEnvioById(Long id) {
    if (envioRepository.existsById(id)) {
        Envio envio = envioRepository.findById(id).get();
        StringBuilder output = new StringBuilder();

        output.append("ID Envío: ").append(envio.getIdEnvio()).append("\n");

        // Datos del pedido
        Pedido pedido = envio.getPedido();
        if (pedido != null) {
            output.append("Pedido ID: ").append(pedido.getIdPedido()).append("\n")
                  .append("Estado Pedido: ").append(pedido.getEstado()).append("\n");

            List<Producto> productos = pedido.getProductos();
            if (productos != null && !productos.isEmpty()) {
                output.append("Productos en el pedido:\n");
                for (Producto producto : productos) {
                    output.append("  - ID: ").append(producto.getIdProducto()).append("\n")
                          .append("    Nombre: ").append(producto.getNombre()).append("\n")
                          .append("    Categoría: ").append(producto.getCategoria()).append("\n")
                          .append("    Precio: $").append(producto.getPrecio()).append("\n")
                          .append("    Stock: ").append(producto.getStock()).append("\n");
                }
            } else {
                output.append("Productos en el pedido: Ninguno\n");
            }
        } else {
            output.append("Pedido: No asignado\n");
        }

        // Datos del cliente
        Cliente cliente = envio.getCliente();
        if (cliente != null) {
            output.append("Cliente ID: ").append(cliente.getIdCliente()).append("\n")
                  .append("Nombre: ").append(cliente.getNombreCliente()).append("\n")
                  .append("Email: ").append(cliente.getEmailCliente()).append("\n")
                  .append("Dirección: ").append(cliente.getDireccionCliente()).append("\n")
                  .append("RUN: ").append(cliente.getRun()).append("\n");
        } else {
            output.append("Cliente: No asignado\n");
        }

        // Datos del envío
        output.append("Dirección Envío: ").append(envio.getDireccionEnvio()).append("\n")
              .append("Fecha Envío: ").append(envio.getFechaEnvio()).append("\n")
              .append("Estado Envío: ").append(envio.getEstadoEnvio()).append("\n");

        return output.toString();
    } else {
        return "No se encontraron envíos con esa ID";
    }
}

    //Agregar
public String addEnvio(Envio envio) {
    envioRepository.save(envio);
    return "Envío agregado correctamente";
}

    //Eliminar
    public String deleteEnvio(Long id){
        if(envioRepository.existsById(id)){
            envioRepository.deleteById(id);
            return "Envio eliminado correctamente";
        }else{
            return "No se encontraron envios con esa ID";
        }
    }
    //Actualizar
    public String updateEnvio(Long id, Envio envio){
        if(envioRepository.existsById(id)){
            Envio buscado = envioRepository.findById(id).get();
            buscado.setPedido(envio.getPedido());
            buscado.setCliente(envio.getCliente());
            buscado.setDireccionEnvio(envio.getDireccionEnvio());
            buscado.setFechaEnvio(envio.getFechaEnvio());
            buscado.setEstadoEnvio(envio.getEstadoEnvio());
            envioRepository.save(buscado);
            return "Envio actualizado correctamente";
        }else{
            return "No se encontraron envios con esa ID";
        }
    }
}
