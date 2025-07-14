package com.FullStack.Prueba2.controller.gestioninventario;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.FullStack.Prueba2.model.gestionInventario.Producto;
import com.FullStack.Prueba2.service.gestioninventario.ProductoService;
import com.FullStack.Prueba2.hateoas.ProductoModelAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;

@RestController
@Tag(name = "Productos", description = "EndPoints de productos")
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoModelAssembler assembler;

    @GetMapping
    @Operation(summary="Lista todos los productos.",description = "Lista todos los productos.")
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getAllProductos() {
        List<EntityModel<Producto>> productos = productoService.getAllProductos().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return ResponseEntity.ok(
            CollectionModel.of(productos,
                linkTo(methodOn(ProductoController.class).getAllProductos()).withSelfRel())
        );
    }

    @GetMapping("/{id}")
    @Operation(summary="Lista un producto.",description = "Lista un producto con ID especificada en el URL.")
    public ResponseEntity<EntityModel<Producto>> getProductoById(@PathVariable Long id) {
        Producto producto = productoService.getProductoById(id);
        return ResponseEntity.ok(assembler.toModel(producto));
    }

    @PostMapping
    @Operation(summary="Registra un nuevo producto.",description = "Registra un nuevo producto con los atributos ingresados.")
    public ResponseEntity<EntityModel<Producto>> addProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.addProducto(producto);
        return ResponseEntity
            .created(linkTo(methodOn(ProductoController.class).getProductoById(nuevoProducto.getIdProducto())).toUri())
            .body(assembler.toModel(nuevoProducto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary="Borra un producto.",description = "Borra un producto con la ID especificada en el URL.")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary="Actualiza un producto.",description = "Actualiza un producto con los atributos ingresados.")
    public ResponseEntity<EntityModel<Producto>> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        Producto productoActualizado = productoService.actualizarProducto(id, producto);
        return ResponseEntity.ok(assembler.toModel(productoActualizado));
    }

    // Manejo de excepción para retornar 404 cuando no se encuentra entidad
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
