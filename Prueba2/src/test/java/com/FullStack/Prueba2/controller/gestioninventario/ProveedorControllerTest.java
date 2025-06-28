package com.FullStack.Prueba2.controller.gestioninventario;

import com.FullStack.Prueba2.model.gestionInventario.Proveedor;
import com.FullStack.Prueba2.service.gestioninventario.ProveedorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;


import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProveedorController.class)
public class ProveedorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProveedorService proveedorService;

    @Autowired
    private ObjectMapper objectMapper;

    private Proveedor proveedor1;
    private Proveedor proveedor2;

    @BeforeEach
    void setUp() {
        proveedor1 = new Proveedor(1L, "Proveedor Uno", "contacto1", "direccion1");
        proveedor2 = new Proveedor(2L, "Proveedor Dos", "contacto2", "direccion2");
    }

    @Test
    void testListarTodos() throws Exception {
        when(proveedorService.obtenerTodos()).thenReturn(Arrays.asList(proveedor1, proveedor2));

        mockMvc.perform(get("/api/proveedores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nombre", is("Proveedor Uno")))
                .andExpect(jsonPath("$[1].nombre", is("Proveedor Dos")));
    }

    @Test
    void testObtenerPorId_Found() throws Exception {
        when(proveedorService.obtenerPorId(1L)).thenReturn(proveedor1);

        mockMvc.perform(get("/api/proveedores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Proveedor Uno")));
    }

    @Test
    void testObtenerPorId_NotFound() throws Exception {
        when(proveedorService.obtenerPorId(999L)).thenReturn(null);

        mockMvc.perform(get("/api/proveedores/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCrear() throws Exception {
        Proveedor nuevoProveedor = new Proveedor(null, "Proveedor Nuevo", "contactoN", "direccionN");
        Proveedor proveedorGuardado = new Proveedor(3L, "Proveedor Nuevo", "contactoN", "direccionN");

        when(proveedorService.guardar(any(Proveedor.class))).thenReturn(proveedorGuardado);

        mockMvc.perform(post("/api/proveedores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevoProveedor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.nombre", is("Proveedor Nuevo")));
    }

    @Test
    void testActualizar_Found() throws Exception {
        Proveedor proveedorActualizado = new Proveedor(null, "Proveedor Actualizado", "contactoA", "direccionA");

        when(proveedorService.obtenerPorId(1L)).thenReturn(proveedor1);
        when(proveedorService.guardar(any(Proveedor.class))).thenReturn(
                new Proveedor(1L, "Proveedor Actualizado", "contactoA", "direccionA"));

        mockMvc.perform(put("/api/proveedores/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(proveedorActualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Proveedor Actualizado")));
    }

    @Test
    void testActualizar_NotFound() throws Exception {
        Proveedor proveedorActualizado = new Proveedor(null, "Proveedor Actualizado", "contactoA", "direccionA");

        when(proveedorService.obtenerPorId(999L)).thenReturn(null);

        mockMvc.perform(put("/api/proveedores/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(proveedorActualizado)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminar_Found() throws Exception {
        when(proveedorService.obtenerPorId(1L)).thenReturn(proveedor1);
        doNothing().when(proveedorService).eliminar(1L);

        mockMvc.perform(delete("/api/proveedores/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEliminar_NotFound() throws Exception {
        when(proveedorService.obtenerPorId(999L)).thenReturn(null);

        mockMvc.perform(delete("/api/proveedores/999"))
                .andExpect(status().isNotFound());
    }
}
