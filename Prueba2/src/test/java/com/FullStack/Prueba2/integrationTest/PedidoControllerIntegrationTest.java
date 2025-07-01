package com.FullStack.Prueba2.integrationTest;

import com.FullStack.Prueba2.model.cliente.Cliente;
import com.FullStack.Prueba2.model.cliente.Pedido;
import com.FullStack.Prueba2.repository.cliente.ClienteRepository;
import com.FullStack.Prueba2.repository.cliente.PedidoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PedidoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Cliente cliente;

    @BeforeEach
    void setup() {
        pedidoRepository.deleteAll();
        clienteRepository.deleteAll();

        cliente = new Cliente();
        cliente.setNombreCliente("Juan Pérez");
        cliente.setRun("12345678-9"); // ← Esto es lo que falta
        cliente.setEmailCliente("juan@example.com");
        cliente.setDireccionCliente("Av. Siempre Viva 123");

        cliente = clienteRepository.save(cliente);
    }

@Test
void testAgregarYObtenerPedido() throws Exception {
    Pedido nuevoPedido = new Pedido();
    nuevoPedido.setEstado("PENDIENTE");
    nuevoPedido.setCliente(cliente);  // Asegura que el cliente esté seteado

    String pedidoJson = objectMapper.writeValueAsString(nuevoPedido);

    // Crear pedido
    String response = mockMvc.perform(post("/api/pedidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(pedidoJson))
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString();

    Pedido pedidoCreado = objectMapper.readValue(response, Pedido.class);

    assertNotNull(pedidoCreado.getIdPedido());
    assertEquals("PENDIENTE", pedidoCreado.getEstado());
}


    @Test
    void testActualizarPedido() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setEstado("Pendiente");
        pedido = pedidoRepository.save(pedido);

        pedido.setEstado("Entregado");
        String updatedJson = objectMapper.writeValueAsString(pedido);

        mockMvc.perform(put("/api/pedidos/" + pedido.getIdPedido())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.estado").value("Entregado"));
    }

    @Test
    void testEliminarPedido() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setEstado("Pendiente");
        pedido = pedidoRepository.save(pedido);

        mockMvc.perform(delete("/api/pedidos/" + pedido.getIdPedido()))
            .andExpect(status().isNoContent());

        assertThat(pedidoRepository.findById(pedido.getIdPedido())).isEmpty();
    }

    @Test
    void testObtenerPedidoPorId() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setEstado("En proceso");
        pedido = pedidoRepository.save(pedido);

        mockMvc.perform(get("/api/pedidos/" + pedido.getIdPedido()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.estado").value("En proceso"));
    }

    @Test
    void testListarTodosLosPedidos() throws Exception {
        Pedido pedido1 = new Pedido();
        pedido1.setCliente(cliente);
        pedido1.setEstado("Pendiente");

        Pedido pedido2 = new Pedido();
        pedido2.setCliente(cliente);
        pedido2.setEstado("Entregado");

        pedidoRepository.saveAll(List.of(pedido1, pedido2));

        mockMvc.perform(get("/api/pedidos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2));
    }
}
