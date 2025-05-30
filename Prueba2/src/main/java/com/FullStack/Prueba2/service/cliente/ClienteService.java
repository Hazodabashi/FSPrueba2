package com.FullStack.Prueba2.service.cliente;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.FullStack.Prueba2.model.cliente.Cliente;
import com.FullStack.Prueba2.model.venta.Venta;
import com.FullStack.Prueba2.repository.cliente.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;


    //Agregar
    public Cliente addCliente(Cliente cliente) {
    return clienteRepository.save(cliente);
    }

    //Listar
    public List<Cliente> getAllClientes() {
    return clienteRepository.findAll();
    }

    //Buscar
    public Cliente getClienteById(Long id) {
    return clienteRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado con ID: " + id));
    }

    //ELiminar
    @Transactional
    public void eliminarCliente(Long idCliente) {
    Cliente cliente = clienteRepository.findById(idCliente)
        .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));

    for (Venta venta : cliente.getVentas()) {
        venta.setCliente(null); // rompe la relación
    }
    cliente.getVentas().clear(); // limpia la colección en el cliente
    clienteRepository.delete(cliente);
    }


    //Actualizar
    public void updateCliente(Long id, Cliente cliente) {
    Cliente cli = clienteRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));

    cli.setNombreCliente(cliente.getNombreCliente());
    cli.setEmailCliente(cliente.getEmailCliente());
    cli.setDireccionCliente(cliente.getDireccionCliente());
    cli.setResenas(cliente.getResenas());
    cli.setPedidos(cliente.getPedidos());

    clienteRepository.save(cli);
    }
}