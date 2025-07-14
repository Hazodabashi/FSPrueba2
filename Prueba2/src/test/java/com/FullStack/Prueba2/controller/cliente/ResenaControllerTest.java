package com.FullStack.Prueba2.controller.cliente;

import com.FullStack.Prueba2.model.cliente.Resena;
import com.FullStack.Prueba2.model.cliente.Cliente;
import com.FullStack.Prueba2.model.gestionInventario.Producto;
import com.FullStack.Prueba2.service.cliente.ResenaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ResenaController.class)
public class ResenaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ResenaService resenaService;

    private Resena getSampleResena() {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(1L);

        Producto producto = new Producto();
        producto.setIdProducto(1L);

        return new Resena(1L, 5, cliente, producto, "Muy bueno");
    }

    @Test
    void testGetAllResenas() throws Exception {
        Mockito.when(resenaService.getAllResenas())
                .thenReturn(Arrays.asList(getSampleResena()));

        mockMvc.perform(get("/api/resenas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].idResena").value(1));
    }

    @Test
    void testObtenerResenaPorId() throws Exception {
        Mockito.when(resenaService.getResenaById(1L))
                .thenReturn(getSampleResena());

        mockMvc.perform(get("/api/resenas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(".idResena").value(1));
    }

    @Test
    void testObtenerResenaPorId_NotFound() throws Exception {
        Mockito.when(resenaService.getResenaById(99L))
                .thenThrow(new EntityNotFoundException("No existe"));

        mockMvc.perform(get("/api/resenas/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAgregarResena() throws Exception {
        Resena resena = getSampleResena();

        Mockito.when(resenaService.addResena(any(Resena.class)))
                .thenReturn(resena);

        mockMvc.perform(post("/api/resenas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resena)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(".idResena").value(1));
    }

    @Test
    void testEliminarResena() throws Exception {
        mockMvc.perform(delete("/api/resenas/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEliminarResena_NotFound() throws Exception {
        Mockito.doThrow(new EntityNotFoundException("No existe"))
                .when(resenaService).eliminarResena(99L);

        mockMvc.perform(delete("/api/resenas/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testActualizarResena() throws Exception {
        Resena actualizada = getSampleResena();
        actualizada.setComentario("Actualizado");

        Mockito.when(resenaService.actualizarResena(eq(1L), any(Resena.class)))
                .thenReturn(actualizada);

        mockMvc.perform(put("/api/resenas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(".comentario").value("Actualizado"));
    }

    @Test
    void testActualizarResena_NotFound() throws Exception {
        Resena resena = getSampleResena();
        Mockito.when(resenaService.actualizarResena(eq(99L), any(Resena.class)))
                .thenThrow(new EntityNotFoundException("No existe"));

        mockMvc.perform(put("/api/resenas/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resena)))
                .andExpect(status().isNotFound());
    }
}
