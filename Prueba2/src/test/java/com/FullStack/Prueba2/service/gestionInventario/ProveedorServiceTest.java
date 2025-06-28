package com.FullStack.Prueba2.service.gestionInventario;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.FullStack.Prueba2.model.gestionInventario.Proveedor;
import com.FullStack.Prueba2.repository.gestioninventario.ProveedorRepository;
import com.FullStack.Prueba2.service.gestioninventario.ProveedorService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProveedorServiceTest {

    @Mock
    private ProveedorRepository proveedorRepository;

    @InjectMocks
    private ProveedorService proveedorService;

    @Test
    void testObtenerTodos() {
        Proveedor p1 = new Proveedor(1L, "Proveedor1", "Contacto1", "Direccion1");
        Proveedor p2 = new Proveedor(2L, "Proveedor2", "Contacto2", "Direccion2");
        when(proveedorRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Proveedor> proveedores = proveedorService.obtenerTodos();

        assertEquals(2, proveedores.size());
        verify(proveedorRepository).findAll();
    }

    @Test
    void testObtenerPorId_Existente() {
        Proveedor p = new Proveedor(1L, "Proveedor1", "Contacto1", "Direccion1");
        when(proveedorRepository.findById(1L)).thenReturn(Optional.of(p));

        Proveedor resultado = proveedorService.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals("Proveedor1", resultado.getNombre());
        verify(proveedorRepository).findById(1L);
    }

    @Test
    void testObtenerPorId_NoExistente() {
        when(proveedorRepository.findById(1L)).thenReturn(Optional.empty());

        Proveedor resultado = proveedorService.obtenerPorId(1L);

        assertNull(resultado);
        verify(proveedorRepository).findById(1L);
    }

    @Test
    void testGuardar() {
        Proveedor p = new Proveedor(null, "ProveedorNuevo", "ContactoNuevo", "DireccionNueva");
        Proveedor pGuardado = new Proveedor(1L, "ProveedorNuevo", "ContactoNuevo", "DireccionNueva");

        when(proveedorRepository.save(p)).thenReturn(pGuardado);

        Proveedor resultado = proveedorService.guardar(p);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("ProveedorNuevo", resultado.getNombre());
        verify(proveedorRepository).save(p);
    }

    @Test
    void testEliminar() {
        doNothing().when(proveedorRepository).deleteById(1L);

        proveedorService.eliminar(1L);

        verify(proveedorRepository).deleteById(1L);
    }
}
