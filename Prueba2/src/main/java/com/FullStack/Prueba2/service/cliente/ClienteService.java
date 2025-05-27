package com.FullStack.Prueba2.service.cliente;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FullStack.Prueba2.model.cliente.Cliente;
import com.FullStack.Prueba2.model.cliente.Pedido;
import com.FullStack.Prueba2.model.cliente.Resena;
import com.FullStack.Prueba2.model.envio.Envio;
import com.FullStack.Prueba2.model.gestionInventario.Producto;
import com.FullStack.Prueba2.model.venta.Venta;
import com.FullStack.Prueba2.repository.cliente.ClienteRepository;
import com.FullStack.Prueba2.repository.cliente.PedidoRepository;
import com.FullStack.Prueba2.repository.cliente.ResenaRepository;
import com.FullStack.Prueba2.repository.envio.EnvioRepository;
import com.FullStack.Prueba2.repository.venta.VentaRepository;

import jakarta.transaction.Transactional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private EnvioRepository envioRepository;
    @Autowired
    private ResenaRepository resenaRepository;



    //Listar
public String getAllClientes() {
    StringBuilder output = new StringBuilder();

    for (Cliente cliente : clienteRepository.findAll()) {
        output.append("\n");
        output.append("ID Cliente: ").append(cliente.getIdCliente()).append("\n");
        output.append("Nombre Cliente: ").append(cliente.getNombreCliente()).append("\n");
        output.append("Email Cliente: ").append(cliente.getEmailCliente()).append("\n");
        output.append("Direccion Cliente: ").append(cliente.getDireccionCliente()).append("\n");

        // Reseñas (evitando toString recursivo)
        if (cliente.getResenas() != null && !cliente.getResenas().isEmpty()) {
            output.append("Reseñas:\n");
            for (Resena resena : cliente.getResenas()) {
                output.append("- ID Reseña: ").append(resena.getIdResena())
                      .append(", Calificación: ").append(resena.getCalificacion())
                      .append(", Comentario: ").append(resena.getComentario());
                if (resena.getProducto() != null) {
                    output.append(", Producto: ").append(resena.getProducto().getNombre());
                }
                output.append("\n");
            }
        } else {
            output.append("Reseñas: Ninguna\n");
        }

        // Pedidos
        if (cliente.getPedidos() != null && !cliente.getPedidos().isEmpty()) {
            output.append("Pedidos:\n");
            for (Pedido pedido : cliente.getPedidos()) {
                output.append("- Pedido ID: ").append(pedido.getIdPedido())
                      .append(", Estado: ").append(pedido.getEstado())
                      .append(", Productos: ");
                if (pedido.getProductos() != null && !pedido.getProductos().isEmpty()) {
                    for (Producto producto : pedido.getProductos()) {
                        output.append(producto.getNombre()).append(" (ID: ")
                              .append(producto.getIdProducto()).append("), ");
                    }
                    output.setLength(output.length() - 2); // Quitar última coma
                } else {
                    output.append("Ninguno");
                }
                output.append("\n");
            }
        } else {
            output.append("Pedidos: Ninguno\n");
        }

        output.append("\n");
        output.append("------------------o------------------\n");
        output.append("\n");
    }

    return output.isEmpty() ? "No se encontraron clientes" : output.toString();
}

    //Buscar
public String getClienteById(Long id) {
    StringBuilder output = new StringBuilder();

    if (clienteRepository.existsById(id)) {
        Cliente buscado = clienteRepository.findById(id).get();

        output.append("\n");
        output.append("ID Cliente: ").append(buscado.getIdCliente()).append("\n");
        output.append("Nombre Cliente: ").append(buscado.getNombreCliente()).append("\n");
        output.append("Email Cliente: ").append(buscado.getEmailCliente()).append("\n");
        output.append("Direccion Cliente: ").append(buscado.getDireccionCliente()).append("\n");
        output.append("Reseñas: ").append(buscado.getResenas()).append("\n");

        if (buscado.getPedidos() != null && !buscado.getPedidos().isEmpty()) {
            output.append("Pedidos:\n");
            for (Pedido pedido : buscado.getPedidos()) {
                output.append("- Pedido ID: ").append(pedido.getIdPedido())
                      .append(", Estado: ").append(pedido.getEstado()).append("\n");
            }
        } else {
            output.append("Pedidos: Ninguno\n");
        }

        output.append("\n");
        output.append("------------------o------------------\n");
        output.append("\n");

        return output.toString();
    } else {
        return "No se encontraron clientes con esa ID";
    }
}


    //Agregar
public String addCliente(Cliente cliente) {
    clienteRepository.save(cliente);
    return "Cliente agregado correctamente";
}


    //ELiminar
    @Transactional
    public String borrarClienteYRelacionados(Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            return "No se encontró el cliente con ID: " + clienteId;
        }

        // Primero borrar los envíos del cliente
        List<Envio> envios = envioRepository.findByClienteIdCliente(clienteId);
        if (!envios.isEmpty()) {
            envioRepository.deleteAll(envios);
        }

        // Borrar las ventas del cliente
        List<Venta> ventas = ventaRepository.findByClienteIdCliente(clienteId);
        if (!ventas.isEmpty()) {
            ventaRepository.deleteAll(ventas);
        }

        // Borrar los pedidos del cliente
        List<Pedido> pedidos = pedidoRepository.findByClienteIdCliente(clienteId);
        if (!pedidos.isEmpty()) {
            pedidoRepository.deleteAll(pedidos);
        }

        // Borrar las reseñas del cliente
        List<Resena> resenas = resenaRepository.findByClienteIdCliente(clienteId);
        if (!resenas.isEmpty()) {
            resenaRepository.deleteAll(resenas);
        }

        // Finalmente, borrar el cliente
        clienteRepository.deleteById(clienteId);

        return "Cliente y sus datos relacionados eliminados correctamente";
    }

    //Actualizar
    public String updateCliente(Long id, Cliente cliente) {
        if(clienteRepository.existsById(id)){
            Cliente buscado = clienteRepository.findById(id).get();
            buscado.setNombreCliente(cliente.getNombreCliente());
            buscado.setEmailCliente(cliente.getEmailCliente());
            buscado.setDireccionCliente(cliente.getDireccionCliente());
            buscado.setResenas(cliente.getResenas());
            buscado.setPedidos(cliente.getPedidos());
            clienteRepository.save(buscado);
            return "Cliente actualizado correctamente";
        }else{
            return "No se encontraron clientes con esa ID";
        }
    }

    
        public String asignarPedidoACliente(Long idCliente, Pedido nuevoPedido) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(idCliente);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            nuevoPedido.setCliente(cliente); // Importante si usas la relación inversa
            pedidoRepository.save(nuevoPedido);
            cliente.getPedidos().add(nuevoPedido);
            clienteRepository.save(cliente);
            return "Pedido asignado correctamente al cliente con ID: " + idCliente;
        } else {
            return "Cliente no encontrado con ID: " + idCliente;
        }
    }
}