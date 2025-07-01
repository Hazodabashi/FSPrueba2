package com.FullStack.Prueba2.integrationTest;

import com.FullStack.Prueba2.model.cliente.Cliente;
import com.FullStack.Prueba2.model.cliente.Resena;
import com.FullStack.Prueba2.model.gestionInventario.Producto;
import com.FullStack.Prueba2.repository.cliente.ClienteRepository;
import com.FullStack.Prueba2.repository.cliente.ResenaRepository;
import com.FullStack.Prueba2.repository.gestioninventario.ProductoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ResenaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ResenaRepository resenaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Cliente cliente;
    private Producto producto;
    private Resena resena;

    @BeforeAll
    void setup() {
        resenaRepository.deleteAll();
        clienteRepository.deleteAll();
        productoRepository.deleteAll();

        // Crear Cliente válido
        cliente = new Cliente();
        cliente.setNombreCliente("Juan Pérez");
        cliente.setEmailCliente("juan@example.com");
        cliente.setDireccionCliente("Calle Falsa 123");
        cliente.setRun("1234567890123");
        cliente = clienteRepository.save(cliente);

        // Crear Producto válido (ajusta según tu modelo)
        producto = new Producto();
        producto.setNombre("Producto Test");
        producto.setDescripcion("Descripción de prueba");
        producto.setPrecio(1000.0);
        producto = productoRepository.save(producto);

        // Crear Resena vinculada a cliente y producto
        resena = new Resena();
        resena.setCalificacion(5);
        resena.setComentario("Muy buen producto");
        resena.setCliente(cliente);
        resena.setProducto(producto);
        resena = resenaRepository.save(resena);
    }

    @Test
    void testGetAllResenas() throws Exception {
        mockMvc.perform(get("/api/resenas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].comentario", is(resena.getComentario())));
    }

    @Test
    void testGetResenaById_Success() throws Exception {
        mockMvc.perform(get("/api/resenas/{id}", resena.getIdResena()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idResena", is(resena.getIdResena().intValue())))
                .andExpect(jsonPath("$.comentario", is(resena.getComentario())))
                .andExpect(jsonPath("$.calificacion", is(resena.getCalificacion())));
    }

    @Test
    void testGetResenaById_NotFound() throws Exception {
        mockMvc.perform(get("/api/resenas/{id}", 99999))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddResena() throws Exception {
        Resena nuevaResena = new Resena();
        nuevaResena.setCalificacion(4);
        nuevaResena.setComentario("Comentario nuevo");
        nuevaResena.setCliente(cliente);
        nuevaResena.setProducto(producto);

        mockMvc.perform(post("/api/resenas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevaResena)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idResena").exists())
                .andExpect(jsonPath("$.comentario", is("Comentario nuevo")))
                .andExpect(jsonPath("$.calificacion", is(4)));
    }

    @Test
    void testEliminarResena_Success() throws Exception {
        Resena resenaToDelete = new Resena();
        resenaToDelete.setCalificacion(3);
        resenaToDelete.setComentario("Comentario a eliminar");
        resenaToDelete.setCliente(cliente);
        resenaToDelete.setProducto(producto);
        resenaToDelete = resenaRepository.save(resenaToDelete);

        mockMvc.perform(delete("/api/resenas/{id}", resenaToDelete.getIdResena()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEliminarResena_NotFound() throws Exception {
        mockMvc.perform(delete("/api/resenas/{id}", 99999))
                .andExpect(status().isNotFound());
    }

    @Test
    void testActualizarResena_Success() throws Exception {
        Resena resenaUpdate = new Resena();
        resenaUpdate.setCalificacion(2);
        resenaUpdate.setComentario("Comentario actualizado");
        resenaUpdate.setCliente(cliente);
        resenaUpdate.setProducto(producto);

        mockMvc.perform(put("/api/resenas/{id}", resena.getIdResena())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resenaUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comentario", is("Comentario actualizado")))
                .andExpect(jsonPath("$.calificacion", is(2)));
    }

    @Test
    void testActualizarResena_NotFound() throws Exception {
        Resena resenaUpdate = new Resena();
        resenaUpdate.setCalificacion(1);
        resenaUpdate.setComentario("Comentario no encontrado");
        resenaUpdate.setCliente(cliente);
        resenaUpdate.setProducto(producto);

        mockMvc.perform(put("/api/resenas/{id}", 99999)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resenaUpdate)))
                .andExpect(status().isNotFound());
    }
}
