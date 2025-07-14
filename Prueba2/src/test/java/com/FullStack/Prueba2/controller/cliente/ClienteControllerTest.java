package com.FullStack.Prueba2.controller.cliente;

import com.FullStack.Prueba2.model.cliente.Cliente;
import com.FullStack.Prueba2.service.cliente.ClienteService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;



import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    private Cliente cliente1;

    @BeforeEach
    void setup() {
        cliente1 = new Cliente();
        cliente1.setIdCliente(1L);
        cliente1.setNombreCliente("Juan Perez");
        cliente1.setEmailCliente("juan@email.com");
        cliente1.setDireccionCliente("Calle 123");
    }

    @Test
    void testGetAllClientes() throws Exception {
        when(clienteService.getAllClientes()).thenReturn(List.of(cliente1));

        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idCliente").value(1L))
                .andExpect(jsonPath("$[0].nombreCliente").value("Juan Perez"));
    }

    @Test
    void testGetClienteById_Existente() throws Exception {
        when(clienteService.getClienteById(1L)).thenReturn(cliente1);

        mockMvc.perform(get("/api/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCliente").value(1L))
                .andExpect(jsonPath("$.nombreCliente").value("Juan Perez"));
    }

    @Test
    void testGetClienteById_NoExistente() throws Exception {
        when(clienteService.getClienteById(anyLong()))
                .thenThrow(new EntityNotFoundException("Cliente no encontrado con ID: 99"));

        mockMvc.perform(get("/api/clientes/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("Cliente no encontrado con ID: 99"));
    }

    @Test
    void testAgregarCliente() throws Exception {
        when(clienteService.addCliente(any(Cliente.class))).thenReturn(cliente1);

        String clienteJson = """
            {
                "nombreCliente": "Juan Perez",
                "emailCliente": "juan@email.com",
                "direccionCliente": "Calle 123"
            }
            """;

        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clienteJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idCliente").value(1L))
                .andExpect(jsonPath("$.nombreCliente").value("Juan Perez"));
    }

    @Test
    void testEliminarCliente_Existente() throws Exception {
        // No hacer nada cuando eliminarCliente es llamado con 1L
        Mockito.doNothing().when(clienteService).eliminarCliente(1L);

        mockMvc.perform(delete("/api/clientes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEliminarCliente_NoExistente() throws Exception {
        doThrow(new EntityNotFoundException()).when(clienteService).eliminarCliente(99L);

        mockMvc.perform(delete("/api/clientes/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testActualizarCliente_Existente() throws Exception {
        Mockito.doNothing().when(clienteService).updateCliente(anyLong(), any(Cliente.class));

        String clienteJson = """
            {
                "nombreCliente": "Juan Actualizado",
                "emailCliente": "juanactualizado@email.com",
                "direccionCliente": "Calle Nueva 456"
            }
            """;

        mockMvc.perform(put("/api/clientes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clienteJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Cliente actualizado correctamente"));
    }

    @Test
    void testActualizarCliente_NoExistente() throws Exception {
        doThrow(new EntityNotFoundException()).when(clienteService).updateCliente(anyLong(), any(Cliente.class));

        String clienteJson = """
            {
                "nombreCliente": "Juan Actualizado",
                "emailCliente": "juanactualizado@email.com",
                "direccionCliente": "Calle Nueva 456"
            }
            """;

        mockMvc.perform(put("/api/clientes/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clienteJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("Cliente no encontrado"));
    }
}
