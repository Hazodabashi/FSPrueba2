package com.FullStack.Prueba2.service.cliente;

import com.FullStack.Prueba2.model.cliente.Resena;
import com.FullStack.Prueba2.repository.cliente.ResenaRepository;
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
class ResenaServiceTest {

    @Mock
    private ResenaRepository resenaRepository;

    @InjectMocks
    private ResenaService resenaService;

    @Test
    void testGetAllResenas() {
        Resena r1 = new Resena(1L, 5, null, null, "Excelente");
        Resena r2 = new Resena(2L, 3, null, null, "Regular");
        when(resenaRepository.findAll()).thenReturn(Arrays.asList(r1, r2));

        List<Resena> result = resenaService.getAllResenas();

        assertEquals(2, result.size());
        verify(resenaRepository).findAll();
    }

    @Test
    void testGetResenaById_Existe() {
        Resena resena = new Resena(1L, 4, null, null, "Muy buena");
        when(resenaRepository.findById(1L)).thenReturn(Optional.of(resena));

        Resena result = resenaService.getResenaById(1L);

        assertEquals(1L, result.getIdResena());
        assertEquals(4, result.getCalificacion());
    }

    @Test
    void testGetResenaById_NoExiste() {
        when(resenaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> resenaService.getResenaById(1L));
    }

    @Test
    void testAddResena() {
        Resena resena = new Resena(null, 5, null, null, "Perfecto");
        when(resenaRepository.save(resena)).thenReturn(resena);

        Resena result = resenaService.addResena(resena);

        assertNotNull(result);
        assertEquals(5, result.getCalificacion());
        verify(resenaRepository).save(resena);
    }

    @Test
    void testEliminarResena_Existe() {
        when(resenaRepository.existsById(1L)).thenReturn(true);

        resenaService.eliminarResena(1L);

        verify(resenaRepository).deleteById(1L);
    }

    @Test
    void testEliminarResena_NoExiste() {
        when(resenaRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> resenaService.eliminarResena(1L));
    }

    @Test
    void testActualizarResena() {
        Resena existente = new Resena(1L, 3, null, null, "Bueno");
        Resena actualizado = new Resena(null, 4, null, null, "Muy bueno");

        when(resenaRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(resenaRepository.save(existente)).thenReturn(existente);

        Resena result = resenaService.actualizarResena(1L, actualizado);

        assertEquals(4, result.getCalificacion());
        assertEquals("Muy bueno", result.getComentario());

        verify(resenaRepository).save(existente);
    }
}
