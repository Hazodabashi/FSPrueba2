package com.FullStack.Prueba2.service.cliente;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import com.FullStack.Prueba2.model.cliente.Cliente;
import com.FullStack.Prueba2.model.venta.Venta;
import com.FullStack.Prueba2.repository.cliente.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void testAddCliente() {
        Cliente cliente = new Cliente();
        cliente.setNombreCliente("Juan");

        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente result = clienteService.addCliente(cliente);

        assertEquals("Juan", result.getNombreCliente());
        verify(clienteRepository).save(cliente);
    }

    @Test
    void testGetAllClientes() {
        Cliente cliente1 = new Cliente();
        Cliente cliente2 = new Cliente();
        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente1, cliente2));

        List<Cliente> clientes = clienteService.getAllClientes();

        assertEquals(2, clientes.size());
        verify(clienteRepository).findAll();
    }

    @Test
    void testGetClienteById_Encontrado() {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(1L);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Cliente result = clienteService.getClienteById(1L);

        assertEquals(1L, result.getIdCliente());
    }

    @Test
    void testGetClienteById_NoEncontrado() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> clienteService.getClienteById(1L));
    }

    @Test
    void testEliminarCliente() {
    Cliente cliente = new Cliente();
    Venta venta1 = new Venta();
    venta1.setCliente(cliente);
    // Usar lista mutable para evitar excepción
    cliente.setVentas(new ArrayList<>(Arrays.asList(venta1)));

    when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

    clienteService.eliminarCliente(1L);

    assertNull(venta1.getCliente());
    assertTrue(cliente.getVentas().isEmpty());
    verify(clienteRepository).delete(cliente);
    }

    @Test
    void testUpdateCliente() {
        Cliente original = new Cliente();
        original.setIdCliente(1L);
        original.setNombreCliente("Juan");

        Cliente actualizado = new Cliente();
        actualizado.setNombreCliente("Pedro");
        actualizado.setEmailCliente("pedro@email.com");
        actualizado.setDireccionCliente("Dirección X");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(original));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(original);

        clienteService.updateCliente(1L, actualizado);

        assertEquals("Pedro", original.getNombreCliente());
        assertEquals("pedro@email.com", original.getEmailCliente());
        assertEquals("Dirección X", original.getDireccionCliente());

        verify(clienteRepository).save(original);
    }
}
