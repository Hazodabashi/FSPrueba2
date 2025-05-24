package com.FullStack.Prueba2.service.venta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FullStack.Prueba2.model.cliente.Cliente;
import com.FullStack.Prueba2.model.gestionInventario.Producto;
import com.FullStack.Prueba2.model.venta.Venta;
import com.FullStack.Prueba2.repository.venta.VentaRepository;

@Service
public class VentaService {
    @Autowired
    private VentaRepository ventaRepository;

    //Listar
public String getAllVentas() {
    StringBuilder output = new StringBuilder();

    for (Venta venta : ventaRepository.findAll()) {
        output.append("ID Venta: ").append(venta.getIdVenta()).append("\n");

        Cliente cliente = venta.getCliente();
        if (cliente != null) {
            output.append("Cliente ID: ").append(cliente.getIdCliente()).append("\n")
                  .append("Nombre: ").append(cliente.getNombreCliente()).append("\n")
                  .append("Email: ").append(cliente.getEmailCliente()).append("\n")
                  .append("Dirección: ").append(cliente.getDireccionCliente()).append("\n")
                  .append("RUN: ").append(cliente.getRun()).append("\n");
        } else {
            output.append("Cliente: No asignado\n");
        }

        List<Producto> productos = venta.getProductosVenta();
        if (productos != null && !productos.isEmpty()) {
            output.append("Productos Asignados:\n");
            for (Producto producto : productos) {
                output.append("- ID: ").append(producto.getIdProducto()).append("\n")
                      .append("  Nombre: ").append(producto.getNombre()).append("\n")
                      .append("  Descripción: ").append(producto.getDescripcion()).append("\n")
                      .append("  Categoría: ").append(producto.getCategoria()).append("\n")
                      .append("  Precio: $").append(producto.getPrecio()).append("\n")
                      .append("  Stock: ").append(producto.getStock()).append("\n");
            }
        } else {
            output.append("Productos Asignados: Ninguno\n");
        }

        output.append("--------------------------------------------------\n");
    }

    return output.length() == 0 ? "No se encontraron ventas" : output.toString();
}

    //Buscar
public String getVentaById(Long id) {
    if (!ventaRepository.existsById(id)) {
        return "No se encontraron ventas con esa ID";
    }

    Venta buscado = ventaRepository.findById(id).get();
    StringBuilder output = new StringBuilder();

    output.append("ID Venta: ").append(buscado.getIdVenta()).append("\n");

    Cliente cliente = buscado.getCliente();
    if (cliente != null) {
        output.append("Cliente ID: ").append(cliente.getIdCliente()).append("\n")
              .append("Nombre: ").append(cliente.getNombreCliente()).append("\n")
              .append("Email: ").append(cliente.getEmailCliente()).append("\n")
              .append("Dirección: ").append(cliente.getDireccionCliente()).append("\n")
              .append("RUN: ").append(cliente.getRun()).append("\n");
    } else {
        output.append("Cliente: No asignado\n");
    }

    List<Producto> productos = buscado.getProductosVenta();
    if (productos != null && !productos.isEmpty()) {
        output.append("Productos Asignados:\n");
        for (Producto producto : productos) {
            output.append("- ID: ").append(producto.getIdProducto()).append("\n")
                  .append("  Nombre: ").append(producto.getNombre()).append("\n")
                  .append("  Descripción: ").append(producto.getDescripcion()).append("\n")
                  .append("  Categoría: ").append(producto.getCategoria()).append("\n")
                  .append("  Precio: $").append(producto.getPrecio()).append("\n")
                  .append("  Stock: ").append(producto.getStock()).append("\n");
        }
    } else {
        output.append("Productos Asignados: Ninguno\n");
    }

    return output.toString();
}

    //Agregar
public String addVenta(Venta venta) {
    ventaRepository.save(venta);
    return "Venta agregada correctamente";
}

    //Eliminar
    public String deleteVenta(Long id) {
        if(ventaRepository.existsById(id)){
            ventaRepository.deleteById(id);
            return "Venta eliminada correctamente";
        }else{
            return "No se encontraron ventas con esa ID";
        }
    }
    //Actualizar
    public String updateVenta(Long id, Venta venta) {
        if(ventaRepository.existsById(id)){
            Venta buscado = ventaRepository.findById(id).get();
            buscado.setCliente(venta.getCliente());
            buscado.setProductosVenta(venta.getProductosVenta());
            ventaRepository.save(buscado);
            return "Venta actualizada correctamente";
        }else{
            return "No se encontraron ventas con esa ID";
        }
    }
}
