package com.FullStack.Prueba2.service.cliente;

import com.FullStack.Prueba2.model.cliente.Pedido;
import com.FullStack.Prueba2.model.cliente.Cliente;
import com.FullStack.Prueba2.model.gestionInventario.Producto;
import com.FullStack.Prueba2.repository.cliente.PedidoRepository;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    @Test
    void testGetAllPedidos() {
        Pedido p1 = new Pedido();
        Pedido p2 = new Pedido();
        when(pedidoRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Pedido> result = pedidoService.getAllPedidos();

        assertEquals(2, result.size());
        verify(pedidoRepository).findAll();
    }

    @Test
    void testGetPedidoById_Existe() {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(1L);
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        Pedido result = pedidoService.getPedidoById(1L);

        assertEquals(1L, result.getIdPedido());
    }

    @Test
    void testGetPedidoById_NoExiste() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> pedidoService.getPedidoById(1L));
    }

    @Test
    void testAddPedido() {
        Pedido pedido = new Pedido();
        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        Pedido result = pedidoService.addPedido(pedido);

        assertNotNull(result);
        verify(pedidoRepository).save(pedido);
    }

    @Test
    void testEliminarPedido_Existe() {
        when(pedidoRepository.existsById(1L)).thenReturn(true);

        pedidoService.eliminarPedido(1L);

        verify(pedidoRepository).deleteById(1L);
    }

    @Test
    void testEliminarPedido_NoExiste() {
        when(pedidoRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> pedidoService.eliminarPedido(1L));
    }

    @Test
    void testActualizarPedido() {
        Pedido original = new Pedido();
        original.setIdPedido(1L);
        original.setEstado("pendiente");

        Pedido actualizado = new Pedido();
        actualizado.setEstado("enviado");
        actualizado.setCliente(new Cliente());
        actualizado.setProductos(Arrays.asList(new Producto()));

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(original));
        when(pedidoRepository.save(original)).thenReturn(original);

        Pedido result = pedidoService.actualizarPedido(1L, actualizado);

        assertEquals("enviado", result.getEstado());
        assertEquals(actualizado.getCliente(), result.getCliente());
        assertEquals(actualizado.getProductos(), result.getProductos());

        verify(pedidoRepository).save(original);
    }
}
