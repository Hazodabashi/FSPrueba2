package com.FullStack.Prueba2.service.venta;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.FullStack.Prueba2.model.cliente.Pedido;
import com.FullStack.Prueba2.model.gestionInventario.Producto;
import com.FullStack.Prueba2.model.venta.Venta;
import com.FullStack.Prueba2.repository.cliente.PedidoRepository;
import com.FullStack.Prueba2.repository.venta.VentaRepository;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

public class VentaServiceTest {

    @InjectMocks
    private VentaService ventaService;

    @Mock
    private VentaRepository ventaRepository;

    @Mock
    private PedidoRepository pedidoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllVentas() {
        List<Venta> ventas = List.of(new Venta(), new Venta());
        when(ventaRepository.findAll()).thenReturn(ventas);

        List<Venta> resultado = ventaService.getAllVentas();

        assertEquals(2, resultado.size());
        verify(ventaRepository, times(1)).findAll();
    }

    @Test
    void testGetVentaByIdFound() {
        Venta venta = new Venta();
        venta.setIdVenta(1L);
        when(ventaRepository.findById(1L)).thenReturn(Optional.of(venta));

        Venta resultado = ventaService.getVentaById(1L);

        assertEquals(1L, resultado.getIdVenta());
    }

    @Test
    void testGetVentaByIdNotFound() {
        when(ventaRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
            () -> ventaService.getVentaById(1L));

        assertTrue(ex.getMessage().contains("Venta no encontrada"));
    }

    @Test
    void testAddVentaCalculaTotalYSeteaPedido() {
        // Crear pedido con productos con precios
        Producto p1 = new Producto();
        p1.setPrecio(100.0);
        Producto p2 = new Producto();
        p2.setPrecio(200.0);

        Pedido pedido = new Pedido();
        pedido.setIdPedido(1L);
        pedido.setProductos(List.of(p1, p2));
        pedido.setEstado("pendiente");

        Venta ventaInput = new Venta();
        ventaInput.setPedido(pedido);

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(ventaRepository.save(any(Venta.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Venta resultado = ventaService.addVenta(ventaInput);

        assertEquals(300.0, resultado.getTotal());
        assertEquals("pagado", pedido.getEstado());
        verify(ventaRepository).save(any(Venta.class));
        verify(pedidoRepository).save(pedido);
    }

    @Test
    void testActualizarVentaExistente() {
        Venta ventaExistente = new Venta();
        ventaExistente.setIdVenta(1L);

        Venta ventaActualizada = new Venta();
        ventaActualizada.setCliente(null); // puedes poner un cliente mock
        ventaActualizada.setPedido(null);  // idem

        when(ventaRepository.findById(1L)).thenReturn(Optional.of(ventaExistente));
        when(ventaRepository.save(any(Venta.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Venta resultado = ventaService.actualizarVenta(1L, ventaActualizada);

        assertNotNull(resultado);
        verify(ventaRepository).save(ventaExistente);
    }

    @Test
    void testEliminarVentaExistente() {
        when(ventaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(ventaRepository).deleteById(1L);

        ventaService.eliminarVenta(1L);

        verify(ventaRepository).deleteById(1L);
    }

    @Test
    void testEliminarVentaNoExistente() {
        when(ventaRepository.existsById(1L)).thenReturn(false);

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
            () -> ventaService.eliminarVenta(1L));

        assertTrue(ex.getMessage().contains("Venta no encontrada"));
    }
}
