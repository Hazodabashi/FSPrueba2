package com.FullStack.Prueba2.integrationTest;

import com.FullStack.Prueba2.model.envio.Envio;
import com.FullStack.Prueba2.model.cliente.Cliente;
import com.FullStack.Prueba2.model.venta.Venta;
import com.FullStack.Prueba2.repository.envio.EnvioRepository;
import com.FullStack.Prueba2.repository.cliente.ClienteRepository;
import com.FullStack.Prueba2.repository.venta.VentaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EnvioControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EnvioRepository envioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        // Limpiar tablas para evitar conflictos en test
        envioRepository.deleteAll();
        ventaRepository.deleteAll();
        clienteRepository.deleteAll();
    }

    @Test
    public void testAddEnvio_Success() throws Exception {
        // Primero crear y guardar un cliente y una venta para las relaciones
    Cliente cliente = new Cliente();
    cliente.setNombreCliente("Juan Perez");
    cliente.setEmailCliente("juan@example.com");
    cliente.setDireccionCliente("Calle Falsa 123");
    cliente.setRun("1234567890123");  // máximo 13 caracteres

// asigna otros campos obligatorios...
cliente = clienteRepository.save(cliente);


        Venta venta = new Venta();
        venta = ventaRepository.save(venta);

        Envio envio = new Envio();
        envio.setCliente(cliente);
        envio.setVenta(venta);
        envio.setDireccionEnvio("Av. Siempre Viva 742");
        envio.setFechaEnvio("2025-07-01");
        envio.setEstadoEnvio("Pendiente");

        mockMvc.perform(post("/api/envios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(envio)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.direccionEnvio").value("Av. Siempre Viva 742"))
                .andExpect(jsonPath("$.estadoEnvio").value("Pendiente"))
                .andExpect(jsonPath("$.cliente.idCliente").value(cliente.getIdCliente()))
                .andExpect(jsonPath("$.venta.idVenta").value(venta.getIdVenta()));
    }

    @Test
    public void testGetAllEnvios_Empty() throws Exception {
        mockMvc.perform(get("/api/envios"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

}
