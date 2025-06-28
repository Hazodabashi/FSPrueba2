package com.FullStack.Prueba2.controller.gestioninventario;

import com.FullStack.Prueba2.model.gestionInventario.Producto;
import com.FullStack.Prueba2.service.gestioninventario.ProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductoService productoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Producto producto1;

    @BeforeEach
    void setUp() {
        producto1 = new Producto();
        producto1.setIdProducto(1L);
        producto1.setNombre("Producto Test");
        producto1.setDescripcion("Descripción");
        producto1.setCategoria("Categoría");
        producto1.setPrecio(100.0);
        producto1.setStock(10);
    }

    @Test
    void testGetAllProductos() throws Exception {
        Mockito.when(productoService.getAllProductos()).thenReturn(Arrays.asList(producto1));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idProducto").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Producto Test"));
    }

    @Test
    void testGetProductoById_Found() throws Exception {
        Mockito.when(productoService.getProductoById(1L)).thenReturn(producto1);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProducto").value(1))
                .andExpect(jsonPath("$.nombre").value("Producto Test"));
    }

    @Test
    void testGetProductoById_NotFound() throws Exception {
        Mockito.when(productoService.getProductoById(999L)).thenThrow(new EntityNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/productos/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddProducto() throws Exception {
        Producto nuevoProducto = new Producto();
        nuevoProducto.setNombre("Nuevo Producto");
        nuevoProducto.setDescripcion("Nueva Descripción");
        nuevoProducto.setCategoria("Nueva Categoría");
        nuevoProducto.setPrecio(50.0);
        nuevoProducto.setStock(5);

        Producto guardado = new Producto();
        guardado.setIdProducto(2L);
        guardado.setNombre(nuevoProducto.getNombre());
        guardado.setDescripcion(nuevoProducto.getDescripcion());
        guardado.setCategoria(nuevoProducto.getCategoria());
        guardado.setPrecio(nuevoProducto.getPrecio());
        guardado.setStock(nuevoProducto.getStock());

        Mockito.when(productoService.addProducto(any(Producto.class))).thenReturn(guardado);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevoProducto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idProducto").value(2))
                .andExpect(jsonPath("$.nombre").value("Nuevo Producto"));
    }

    @Test
    void testActualizarProducto_Found() throws Exception {
        Producto actualizado = new Producto();
        actualizado.setIdProducto(1L);
        actualizado.setNombre("Producto Actualizado");
        actualizado.setDescripcion("Descripción Actualizada");
        actualizado.setCategoria("Categoría Actualizada");
        actualizado.setPrecio(150.0);
        actualizado.setStock(20);

        Mockito.when(productoService.actualizarProducto(eq(1L), any(Producto.class))).thenReturn(actualizado);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/productos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Producto Actualizado"));
    }

    @Test
    void testActualizarProducto_NotFound() throws Exception {
        Producto actualizado = new Producto();
        actualizado.setNombre("Producto Actualizado");

        Mockito.when(productoService.actualizarProducto(eq(999L), any(Producto.class)))
                .thenThrow(new EntityNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/productos/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminarProducto_Found() throws Exception {
        Mockito.doNothing().when(productoService).eliminarProducto(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/productos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEliminarProducto_NotFound() throws Exception {
        Mockito.doThrow(new EntityNotFoundException()).when(productoService).eliminarProducto(999L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/productos/999"))
                .andExpect(status().isNotFound());
    }
}
