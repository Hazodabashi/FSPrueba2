package com.FullStack.Prueba2.controller.venta;

import com.FullStack.Prueba2.model.cliente.Cliente;
import com.FullStack.Prueba2.model.cliente.Pedido;
import com.FullStack.Prueba2.model.venta.Venta;
import com.FullStack.Prueba2.service.venta.VentaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VentaController.class)
public class VentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VentaService ventaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllVentas() throws Exception {
        Venta venta1 = new Venta(1L, new Cliente(), new Pedido(), 100.0);
        Venta venta2 = new Venta(2L, new Cliente(), new Pedido(), 200.0);

        Mockito.when(ventaService.getAllVentas()).thenReturn(List.of(venta1, venta2));

        mockMvc.perform(get("/api/ventas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testGetVentaById_Found() throws Exception {
        Venta venta = new Venta(1L, new Cliente(), new Pedido(), 100.0);

        Mockito.when(ventaService.getVentaById(1L)).thenReturn(venta);

        mockMvc.perform(get("/api/ventas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idVenta").value(1));
    }

    @Test
    public void testGetVentaById_NotFound() throws Exception {
        Mockito.when(ventaService.getVentaById(999L))
                .thenThrow(new EntityNotFoundException("Venta no encontrada con ID: 999"));

        mockMvc.perform(get("/api/ventas/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddVenta() throws Exception {
        Venta venta = new Venta(null, new Cliente(), new Pedido(), null);
        Venta ventaGuardada = new Venta(1L, new Cliente(), new Pedido(), 150.0);

        Mockito.when(ventaService.addVenta(any(Venta.class))).thenReturn(ventaGuardada);

        mockMvc.perform(post("/api/ventas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(venta)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idVenta").value(1));
    }

    @Test
    public void testDeleteVenta_Success() throws Exception {
        Mockito.doNothing().when(ventaService).eliminarVenta(1L);

        mockMvc.perform(delete("/api/ventas/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteVenta_NotFound() throws Exception {
        doThrow(new EntityNotFoundException("Venta no encontrada con ID: 999"))
                .when(ventaService).eliminarVenta(999L);

        mockMvc.perform(delete("/api/ventas/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateVenta_Success() throws Exception {
        Venta venta = new Venta(null, new Cliente(), new Pedido(), null);
        Venta ventaActualizada = new Venta(1L, new Cliente(), new Pedido(), 200.0);

        Mockito.when(ventaService.actualizarVenta(eq(1L), any(Venta.class))).thenReturn(ventaActualizada);

        mockMvc.perform(put("/api/ventas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(venta)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idVenta").value(1));
    }

    @Test
    public void testUpdateVenta_NotFound() throws Exception {
        Venta venta = new Venta(null, new Cliente(), new Pedido(), null);

        Mockito.when(ventaService.actualizarVenta(eq(999L), any(Venta.class)))
                .thenThrow(new EntityNotFoundException("Venta no encontrada con ID: 999"));

        mockMvc.perform(put("/api/ventas/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(venta)))
                .andExpect(status().isNotFound());
    }
}
