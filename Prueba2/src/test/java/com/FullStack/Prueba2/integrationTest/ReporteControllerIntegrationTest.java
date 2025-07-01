package com.FullStack.Prueba2.integrationTest;

import com.FullStack.Prueba2.model.cliente.Reporte;
import com.FullStack.Prueba2.repository.cliente.ReporteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReporteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReporteRepository reporteRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Reporte reporte1;

    @BeforeAll
    void setup() {
        reporteRepository.deleteAll();

        reporte1 = new Reporte();
        reporte1.setTipo("Reporte Tipo A");
        reporte1.setFechaGeneracion(new Date(System.currentTimeMillis()));
        reporte1.setDatos("Datos de prueba para reporte");

        reporte1 = reporteRepository.save(reporte1);
    }

    @Test
    void testGetAllReportes() throws Exception {
        mockMvc.perform(get("/api/reportes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].tipo", is(reporte1.getTipo())));
    }

    @Test
    void testGetReporteById_Success() throws Exception {
    reporte1 = reporteRepository.findById(reporte1.getId()).orElseThrow();

    mockMvc.perform(get("/api/reportes/{id}", reporte1.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(reporte1.getId().intValue())))
            .andExpect(jsonPath("$.tipo", is(reporte1.getTipo())))
            .andExpect(jsonPath("$.datos", is(reporte1.getDatos())));
}


    @Test
    void testGetReporteById_NotFound() throws Exception {
        mockMvc.perform(get("/api/reportes/{id}", 99999))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Reporte no encontrado")));
    }

    @Test
    void testAddReporte() throws Exception {
        Reporte nuevoReporte = new Reporte();
        nuevoReporte.setTipo("Reporte Tipo B");
        nuevoReporte.setFechaGeneracion(new Date(System.currentTimeMillis()));
        nuevoReporte.setDatos("Datos nuevos");

        mockMvc.perform(post("/api/reportes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevoReporte)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.tipo", is("Reporte Tipo B")))
                .andExpect(jsonPath("$.datos", is("Datos nuevos")));
    }

    @Test
    void testEliminarReporte_Success() throws Exception {
        Reporte reporteToDelete = new Reporte();
        reporteToDelete.setTipo("Reporte a eliminar");
        reporteToDelete.setFechaGeneracion(new Date(System.currentTimeMillis()));
        reporteToDelete.setDatos("Datos eliminación");

        reporteToDelete = reporteRepository.save(reporteToDelete);

        mockMvc.perform(delete("/api/reportes/{id}", reporteToDelete.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEliminarReporte_NotFound() throws Exception {
        mockMvc.perform(delete("/api/reportes/{id}", 99999))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Reporte no encontrado")));
    }

    @Test
    void testActualizarReporte_Success() throws Exception {
        Reporte reporteUpdate = new Reporte();
        reporteUpdate.setTipo("Reporte actualizado");
        reporteUpdate.setFechaGeneracion(new Date(System.currentTimeMillis()));
        reporteUpdate.setDatos("Datos actualizados");

        mockMvc.perform(put("/api/reportes/{id}", reporte1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reporteUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipo", is("Reporte actualizado")))
                .andExpect(jsonPath("$.datos", is("Datos actualizados")));
    }

    @Test
    void testActualizarReporte_NotFound() throws Exception {
        Reporte reporteUpdate = new Reporte();
        reporteUpdate.setTipo("Reporte no encontrado");
        reporteUpdate.setFechaGeneracion(new Date(System.currentTimeMillis()));
        reporteUpdate.setDatos("Datos no encontrados");

        mockMvc.perform(put("/api/reportes/{id}", 99999)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reporteUpdate)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Reporte no encontrado")));
    }
}
