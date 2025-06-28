package com.FullStack.Prueba2.service.gestionInventario;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.FullStack.Prueba2.model.gestionInventario.Producto;
import com.FullStack.Prueba2.model.gestionInventario.Proveedor;
import com.FullStack.Prueba2.repository.gestioninventario.ProductoRepository;
import com.FullStack.Prueba2.repository.gestioninventario.ProveedorRepository;
import com.FullStack.Prueba2.service.gestioninventario.ProductoService;

import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private ProveedorRepository proveedorRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    void testGetAllProductos() {
        Producto p1 = new Producto();
        Producto p2 = new Producto();
        when(productoRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Producto> productos = productoService.getAllProductos();

        assertEquals(2, productos.size());
        verify(productoRepository).findAll();
    }

    @Test
    void testGetProductoById_Encontrado() {
        Producto producto = new Producto();
        producto.setIdProducto(1L);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Producto resultado = productoService.getProductoById(1L);

        assertEquals(1L, resultado.getIdProducto());
    }

    @Test
    void testGetProductoById_NoEncontrado() {
        when(productoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productoService.getProductoById(1L));
    }

    @Test
    void testAddProducto_ConProveedorDisponible() {
        Producto producto = new Producto();
        Proveedor proveedor = new Proveedor();
        proveedor.setId(10L);

        when(proveedorRepository.findAll()).thenReturn(Collections.singletonList(proveedor));
        when(productoRepository.save(any(Producto.class))).thenAnswer(i -> i.getArgument(0));

        Producto result = productoService.addProducto(producto);

        assertNotNull(result.getProveedor());
        assertEquals(proveedor.getId(), result.getProveedor().getId());
        verify(productoRepository).save(producto);
    }

    @Test
    void testAddProducto_SinProveedor() {
        when(proveedorRepository.findAll()).thenReturn(Collections.emptyList());

        Producto producto = new Producto();

        assertThrows(IllegalStateException.class, () -> productoService.addProducto(producto));
    }

    @Test
    void testEliminarProducto_Existente() {
        when(productoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(productoRepository).deleteById(1L);

        productoService.eliminarProducto(1L);

        verify(productoRepository).deleteById(1L);
    }

    @Test
    void testEliminarProducto_NoExistente() {
        when(productoRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> productoService.eliminarProducto(1L));
    }

    @Test
    void testActualizarProducto_Existente() {
        Producto original = new Producto();
        original.setIdProducto(1L);
        original.setNombre("Viejo nombre");

        Producto actualizado = new Producto();
        actualizado.setStock(10);
        actualizado.setNombre("Nuevo nombre");
        actualizado.setDescripcion("Desc");
        actualizado.setCategoria("Cat");
        actualizado.setPrecio(100.0);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(original));
        when(productoRepository.save(any(Producto.class))).thenAnswer(i -> i.getArgument(0));

        Producto result = productoService.actualizarProducto(1L, actualizado);

        assertEquals(10, result.getStock());
        assertEquals("Nuevo nombre", result.getNombre());
        assertEquals("Desc", result.getDescripcion());
        assertEquals("Cat", result.getCategoria());
        assertEquals(100.0, result.getPrecio());

        verify(productoRepository).save(original);
    }

    @Test
    void testActualizarProducto_NoExistente() {
        when(productoRepository.findById(1L)).thenReturn(Optional.empty());

        Producto actualizado = new Producto();

        assertThrows(EntityNotFoundException.class, () -> productoService.actualizarProducto(1L, actualizado));
    }
}
