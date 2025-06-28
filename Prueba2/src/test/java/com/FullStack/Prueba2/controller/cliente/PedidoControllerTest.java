package com.FullStack.Prueba2.controller.cliente;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.FullStack.Prueba2.model.cliente.Pedido;
import com.FullStack.Prueba2.service.cliente.PedidoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class PedidoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PedidoService pedidoService;

    @InjectMocks
    private PedidoController pedidoController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pedidoController).build();
    }

    @Test
    public void testGetAllPedidos() throws Exception {
        Pedido p1 = new Pedido();
        p1.setIdPedido(1L);
        p1.setEstado("pendiente");

        Pedido p2 = new Pedido();
        p2.setIdPedido(2L);
        p2.setEstado("pagado");

        List<Pedido> pedidos = Arrays.asList(p1, p2);

        when(pedidoService.getAllPedidos()).thenReturn(pedidos);

        mockMvc.perform(get("/api/pedidos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(2))
            .andExpect(jsonPath("$[0].idPedido").value(1))
            .andExpect(jsonPath("$[0].estado").value("pendiente"));
    }

    @Test
    public void testObtenerPedidoPorId() throws Exception {
        Pedido p = new Pedido();
        p.setIdPedido(1L);
        p.setEstado("pendiente");

        when(pedidoService.getPedidoById(1L)).thenReturn(p);

        mockMvc.perform(get("/api/pedidos/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.idPedido").value(1))
            .andExpect(jsonPath("$.estado").value("pendiente"));
    }

    @Test
    public void testObtenerPedidoPorId_NotFound() throws Exception {
        when(pedidoService.getPedidoById(99L)).thenThrow(new EntityNotFoundException("Pedido no encontrado con ID: 99"));

        mockMvc.perform(get("/api/pedidos/99"))
            .andExpect(status().isNotFound());  // porque no hay manejo de excepción en controller
    }

    @Test
    public void testAgregarPedido() throws Exception {
        Pedido p = new Pedido();
        p.setEstado("pendiente");

        Pedido savedPedido = new Pedido();
        savedPedido.setIdPedido(1L);
        savedPedido.setEstado("pendiente");

        when(pedidoService.addPedido(any(Pedido.class))).thenReturn(savedPedido);

        mockMvc.perform(post("/api/pedidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(p)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.idPedido").value(1))
            .andExpect(jsonPath("$.estado").value("pendiente"));
    }

    @Test
    public void testEliminarPedido() throws Exception {
        doNothing().when(pedidoService).eliminarPedido(1L);

        mockMvc.perform(delete("/api/pedidos/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    public void testActualizarPedido() throws Exception {
        Pedido p = new Pedido();
        p.setEstado("pagado");

        Pedido updatedPedido = new Pedido();
        updatedPedido.setIdPedido(1L);
        updatedPedido.setEstado("pagado");

        when(pedidoService.actualizarPedido(eq(1L), any(Pedido.class))).thenReturn(updatedPedido);

        mockMvc.perform(put("/api/pedidos/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(p)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.idPedido").value(1))
            .andExpect(jsonPath("$.estado").value("pagado"));
    }
}
