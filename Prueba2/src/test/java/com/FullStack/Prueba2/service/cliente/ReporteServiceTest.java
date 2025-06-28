package com.FullStack.Prueba2.service.cliente;

import com.FullStack.Prueba2.model.cliente.Reporte;
import com.FullStack.Prueba2.repository.cliente.ReporteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReporteServiceTest {

    @Mock
    private ReporteRepository reporteRepository;

    @InjectMocks
    private ReporteService reporteService;

    @Test
    void testGetAllReportes() {
        Reporte r1 = new Reporte(1L, "Tipo1", new Date(System.currentTimeMillis()), "Datos1");
        Reporte r2 = new Reporte(2L, "Tipo2", new Date(System.currentTimeMillis()), "Datos2");
        when(reporteRepository.findAll()).thenReturn(Arrays.asList(r1, r2));

        List<Reporte> result = reporteService.getAllReportes();

        assertEquals(2, result.size());
        verify(reporteRepository).findAll();
    }

    @Test
    void testGetReporteById_Existe() {
        Reporte reporte = new Reporte(1L, "Tipo", new Date(System.currentTimeMillis()), "Datos");
        when(reporteRepository.findById(1L)).thenReturn(Optional.of(reporte));

        Reporte result = reporteService.getReporteById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Tipo", result.getTipo());
    }

    @Test
    void testGetReporteById_NoExiste() {
        when(reporteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> reporteService.getReporteById(1L));
    }

    @Test
    void testAddReporte() {
        Reporte reporte = new Reporte(null, "TipoNuevo", new Date(System.currentTimeMillis()), "DatosNuevo");
        when(reporteRepository.save(reporte)).thenReturn(reporte);

        Reporte result = reporteService.addReporte(reporte);

        assertNotNull(result);
        assertEquals("TipoNuevo", result.getTipo());
        verify(reporteRepository).save(reporte);
    }

    @Test
    void testEliminarReporte_Existe() {
        when(reporteRepository.existsById(1L)).thenReturn(true);

        reporteService.eliminarReporte(1L);

        verify(reporteRepository).deleteById(1L);
    }

    @Test
    void testEliminarReporte_NoExiste() {
        when(reporteRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> reporteService.eliminarReporte(1L));
    }

    @Test
    void testActualizarReporte() {
        Reporte existente = new Reporte(1L, "TipoAntiguo", new Date(100000), "DatosAntiguos");
        Reporte actualizado = new Reporte(null, "TipoNuevo", new Date(200000), "DatosNuevos");

        when(reporteRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(reporteRepository.save(existente)).thenReturn(existente);

        Reporte result = reporteService.actualizarReporte(1L, actualizado);

        assertEquals("TipoNuevo", result.getTipo());
        assertEquals(new Date(200000), result.getFechaGeneracion());
        assertEquals("DatosNuevos", result.getDatos());

        verify(reporteRepository).save(existente);
    }
}
