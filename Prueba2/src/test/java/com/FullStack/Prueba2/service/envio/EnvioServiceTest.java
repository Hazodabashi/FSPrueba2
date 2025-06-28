package com.FullStack.Prueba2.service.envio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.FullStack.Prueba2.model.envio.Envio;
import com.FullStack.Prueba2.repository.envio.EnvioRepository;

import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EnvioServiceTest {

    @Mock
    private EnvioRepository envioRepository;

    @InjectMocks
    private EnvioService envioService;

    @Test
    void testGetAllEnvios() {
        Envio envio1 = new Envio();
        Envio envio2 = new Envio();
        when(envioRepository.findAll()).thenReturn(Arrays.asList(envio1, envio2));

        List<Envio> envios = envioService.getAllEnvios();

        assertEquals(2, envios.size());
        verify(envioRepository).findAll();
    }

    @Test
    void testGetEnvioById_Encontrado() {
        Envio envio = new Envio();
        envio.setIdEnvio(1L);
        when(envioRepository.findById(1L)).thenReturn(Optional.of(envio));

        Envio resultado = envioService.getEnvioById(1L);

        assertEquals(1L, resultado.getIdEnvio());
        verify(envioRepository).findById(1L);
    }

    @Test
    void testGetEnvioById_NoEncontrado() {
        when(envioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> envioService.getEnvioById(1L));
        verify(envioRepository).findById(1L);
    }

    @Test
    void testAddEnvio() {
        Envio envio = new Envio();
        envio.setDireccionEnvio("Calle Falsa 123");

        when(envioRepository.save(envio)).thenReturn(envio);

        Envio resultado = envioService.addEnvio(envio);

        assertEquals("Calle Falsa 123", resultado.getDireccionEnvio());
        verify(envioRepository).save(envio);
    }

    @Test
    void testEliminarEnvio_Existente() {
        when(envioRepository.existsById(1L)).thenReturn(true);

        envioService.eliminarEnvio(1L);

        verify(envioRepository).existsById(1L);
        verify(envioRepository).deleteById(1L);
    }

    @Test
    void testEliminarEnvio_NoExistente() {
        when(envioRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> envioService.eliminarEnvio(1L));

        verify(envioRepository).existsById(1L);
        verify(envioRepository, never()).deleteById(anyLong());
    }

    @Test
    void testActualizarEnvio_Existente() {
        Envio envioExistente = new Envio();
        envioExistente.setIdEnvio(1L);
        envioExistente.setDireccionEnvio("Vieja dirección");
        envioExistente.setFechaEnvio("2023-01-01");
        envioExistente.setEstadoEnvio("Pendiente");

        Envio envioActualizado = new Envio();
        envioActualizado.setDireccionEnvio("Nueva dirección");
        envioActualizado.setFechaEnvio("2023-06-01");
        envioActualizado.setEstadoEnvio("Entregado");

        when(envioRepository.findById(1L)).thenReturn(Optional.of(envioExistente));
        when(envioRepository.save(any(Envio.class))).thenReturn(envioExistente);

        Envio resultado = envioService.actualizarEnvio(1L, envioActualizado);

        assertEquals("Nueva dirección", resultado.getDireccionEnvio());
        assertEquals("2023-06-01", resultado.getFechaEnvio());
        assertEquals("Entregado", resultado.getEstadoEnvio());

        verify(envioRepository).findById(1L);
        verify(envioRepository).save(envioExistente);
    }

    @Test
    void testActualizarEnvio_NoExistente() {
        Envio envioActualizado = new Envio();

        when(envioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> envioService.actualizarEnvio(1L, envioActualizado));

        verify(envioRepository).findById(1L);
        verify(envioRepository, never()).save(any());
    }
}
