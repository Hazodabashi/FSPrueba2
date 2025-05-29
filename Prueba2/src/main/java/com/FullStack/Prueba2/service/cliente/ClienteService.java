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

    // 🔁 1. Romper relaciones que no deben eliminarse
    for (Venta venta : cliente.getVentas()) {
        venta.setCliente(null); // rompe la relación
    }
    cliente.getVentas().clear(); // limpia la colección en el cliente

    // 🔁 2. (Opcional) Romper otras relaciones si quieres preservarlas

    // ❌ 3. Borrar el cliente
    clienteRepository.delete(cliente);
}


    //Actualizar
public void updateCliente(Long id, Cliente cliente) {
    Cliente buscado = clienteRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));

    buscado.setNombreCliente(cliente.getNombreCliente());
    buscado.setEmailCliente(cliente.getEmailCliente());
    buscado.setDireccionCliente(cliente.getDireccionCliente());
    buscado.setResenas(cliente.getResenas());
    buscado.setPedidos(cliente.getPedidos());

    clienteRepository.save(buscado);
}
}