package com.FullStack.Prueba2.controller.envio;

import com.FullStack.Prueba2.model.envio.Envio;
import com.FullStack.Prueba2.service.envio.EnvioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EnvioController.class)
public class EnvioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EnvioService envioService;  // <-- Aquí está el @MockBean con Mockito para Spring Boot 3.4

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllEnvios() throws Exception {
        Envio e1 = new Envio(1L, null, null, "Dir1", "2025-06-28", "Pendiente");
        Envio e2 = new Envio(2L, null, null, "Dir2", "2025-06-29", "Enviado");
        List<Envio> lista = Arrays.asList(e1, e2);

        Mockito.when(envioService.getAllEnvios()).thenReturn(lista);

        mockMvc.perform(get("/api/envios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].direccionEnvio").value("Dir1"))
                .andExpect(jsonPath("$[1].estadoEnvio").value("Enviado"));
    }

    @Test
    public void testGetEnvioById() throws Exception {
        Envio e = new Envio(1L, null, null, "Dir1", "2025-06-28", "Pendiente");

        Mockito.when(envioService.getEnvioById(1L)).thenReturn(e);

        mockMvc.perform(get("/api/envios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.direccionEnvio").value("Dir1"))
                .andExpect(jsonPath("$.estadoEnvio").value("Pendiente"));
    }

    @Test
    public void testGetEnvioById_NotFound() throws Exception {
        Mockito.when(envioService.getEnvioById(1L))
                .thenThrow(new EntityNotFoundException("Envio no encontrado"));

        mockMvc.perform(get("/api/envios/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddEnvio() throws Exception {
        Envio envio = new Envio(null, null, null, "Dir1", "2025-06-28", "Pendiente");
        Envio envioGuardado = new Envio(1L, null, null, "Dir1", "2025-06-28", "Pendiente");

        Mockito.when(envioService.addEnvio(any(Envio.class))).thenReturn(envioGuardado);

        mockMvc.perform(post("/api/envios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(envio)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idEnvio").value(1))
                .andExpect(jsonPath("$.direccionEnvio").value("Dir1"));
    }

    @Test
    public void testEliminarEnvio() throws Exception {
        Mockito.doNothing().when(envioService).eliminarEnvio(1L);

        mockMvc.perform(delete("/api/envios/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testEliminarEnvio_NotFound() throws Exception {
        Mockito.doThrow(new EntityNotFoundException("Envio no encontrado")).when(envioService).eliminarEnvio(1L);

        mockMvc.perform(delete("/api/envios/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testActualizarEnvio() throws Exception {
        Envio envioActualizado = new Envio(1L, null, null, "DirNueva", "2025-07-01", "Entregado");

        Mockito.when(envioService.actualizarEnvio(eq(1L), any(Envio.class))).thenReturn(envioActualizado);

        mockMvc.perform(put("/api/envios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(envioActualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.direccionEnvio").value("DirNueva"))
                .andExpect(jsonPath("$.estadoEnvio").value("Entregado"));
    }

    @Test
    public void testActualizarEnvio_NotFound() throws Exception {
        Envio envio = new Envio(null, null, null, "Dir1", "2025-06-28", "Pendiente");

        Mockito.when(envioService.actualizarEnvio(eq(1L), any(Envio.class)))
                .thenThrow(new EntityNotFoundException("Envio no encontrado"));

        mockMvc.perform(put("/api/envios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(envio)))
                .andExpect(status().isNotFound());
}
}
