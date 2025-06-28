package com.FullStack.Prueba2.controller.cliente;

import com.FullStack.Prueba2.model.cliente.Reporte;
import com.FullStack.Prueba2.service.cliente.ReporteService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;


import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ReporteController.class)
public class ReporteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReporteService reporteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllReportes() throws Exception {
        Reporte reporte1 = new Reporte(1L, "Ventas", Date.valueOf("2024-01-01"), "datos1");
        Reporte reporte2 = new Reporte(2L, "Compras", Date.valueOf("2024-02-01"), "datos2");

        given(reporteService.getAllReportes()).willReturn(Arrays.asList(reporte1, reporte2));

        mockMvc.perform(get("/api/reportes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void testGetReporteById_found() throws Exception {
        Reporte reporte = new Reporte(1L, "Ventas", Date.valueOf("2024-01-01"), "datos1");
        given(reporteService.getReporteById(1L)).willReturn(reporte);

        mockMvc.perform(get("/api/reportes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipo").value("Ventas"));
    }

    @Test
    void testGetReporteById_notFound() throws Exception {
        given(reporteService.getReporteById(99L)).willThrow(new jakarta.persistence.EntityNotFoundException("Reporte no encontrado"));

        mockMvc.perform(get("/api/reportes/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddReporte() throws Exception {
        Reporte nuevo = new Reporte(null, "Ventas", Date.valueOf("2024-01-01"), "info");
        Reporte guardado = new Reporte(1L, "Ventas", Date.valueOf("2024-01-01"), "info");

        given(reporteService.addReporte(Mockito.any(Reporte.class))).willReturn(guardado);

        mockMvc.perform(post("/api/reportes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testActualizarReporte() throws Exception {
        Reporte actualizado = new Reporte(1L, "Actualizado", Date.valueOf("2024-03-01"), "nuevos datos");
        given(reporteService.actualizarReporte(Mockito.eq(1L), Mockito.any(Reporte.class))).willReturn(actualizado);

        mockMvc.perform(put("/api/reportes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipo").value("Actualizado"));
    }

    @Test
    void testEliminarReporte() throws Exception {
        doNothing().when(reporteService).eliminarReporte(1L);

        mockMvc.perform(delete("/api/reportes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEliminarReporte_notFound() throws Exception {
        doThrow(new jakarta.persistence.EntityNotFoundException("No existe"))
                .when(reporteService).eliminarReporte(99L);

        mockMvc.perform(delete("/api/reportes/99"))
                .andExpect(status().isNotFound());
    }
}
