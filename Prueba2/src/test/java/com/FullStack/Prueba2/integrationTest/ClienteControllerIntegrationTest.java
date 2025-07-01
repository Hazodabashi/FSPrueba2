package com.FullStack.Prueba2.integrationTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.FullStack.Prueba2.model.cliente.Cliente;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ClienteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllClientes() throws Exception {
        mockMvc.perform(get("/api/clientes"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testAgregarCliente() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNombreCliente("Juan");
        cliente.setEmailCliente("juan@example.com");
        cliente.setDireccionCliente("Calle Falsa 123");
        cliente.setRun("1234567890123");

        mockMvc.perform(post("/api/clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(cliente)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.nombreCliente").value("Juan"))
            .andExpect(jsonPath("$.emailCliente").value("juan@example.com"));
    }

    @Test
    void testObtenerClientePorId_notFound() throws Exception {
        mockMvc.perform(get("/api/clientes/999999"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value("error"));
    }

@Test
void testActualizarCliente() throws Exception {
    Cliente cliente = new Cliente();
    cliente.setNombreCliente("Ana");
    cliente.setEmailCliente("ana@example.com");
    cliente.setDireccionCliente("Av Siempre Viva 742");
    cliente.setRun("9876543210123");

    String response = mockMvc.perform(post("/api/clientes")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(cliente)))
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString();

    Cliente clienteGuardado = objectMapper.readValue(response, Cliente.class);

    Cliente clienteActualizar = new Cliente();
    clienteActualizar.setIdCliente(clienteGuardado.getIdCliente());
    clienteActualizar.setNombreCliente("Ana Actualizada");
    clienteActualizar.setEmailCliente(clienteGuardado.getEmailCliente());
    clienteActualizar.setDireccionCliente(clienteGuardado.getDireccionCliente());
    clienteActualizar.setRun(clienteGuardado.getRun());

    mockMvc.perform(put("/api/clientes/" + clienteActualizar.getIdCliente())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(clienteActualizar)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("success"));
}


    @Test
    void testEliminarCliente() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNombreCliente("Carlos");
        cliente.setEmailCliente("carlos@example.com");
        cliente.setDireccionCliente("Av Principal 555");
        cliente.setRun("1928374650912");

        String response = mockMvc.perform(post("/api/clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(cliente)))
            .andExpect(status().isNoContent())
            .andReturn().getResponse().getContentAsString();

        Cliente clienteGuardado = objectMapper.readValue(response, Cliente.class);

        mockMvc.perform(delete("/api/clientes/" + clienteGuardado.getIdCliente()))
            .andExpect(status().isNoContent());
    }

}
