package com.FullStack.Prueba2.controller.gestioninventario;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.FullStack.Prueba2.model.gestionInventario.Proveedor;
import com.FullStack.Prueba2.service.gestioninventario.ProveedorService;
import com.FullStack.Prueba2.hateoas.ProveedorModelAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Proveedores", description = "EndPoints de proveedores.")
@RequestMapping("/api/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private ProveedorModelAssembler assembler;

    @GetMapping
    @Operation(summary="Lista todos los proveedores.", description = "Lista todos los proveedores")
    public ResponseEntity<CollectionModel<EntityModel<Proveedor>>> listarTodos() {
        List<EntityModel<Proveedor>> proveedores = proveedorService.obtenerTodos().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return ResponseEntity.ok(
            CollectionModel.of(proveedores,
                linkTo(methodOn(ProveedorController.class).listarTodos()).withSelfRel())
        );
    }

    @GetMapping("/{id}")
    @Operation(summary="Lista un proveedor.", description = "Lista un proveedor con la ID especificada en el URL.")
    public ResponseEntity<EntityModel<Proveedor>> obtenerPorId(@PathVariable Long id) {
        Proveedor proveedor = proveedorService.obtenerPorId(id);
        if (proveedor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(proveedor));
    }

    @PostMapping
    @Operation(summary="Registra un nuevo proveedor.", description = "Registra un nuevo proveedor con los atributos ingresados.")
    public ResponseEntity<EntityModel<Proveedor>> crear(@RequestBody Proveedor proveedor) {
        Proveedor nuevoProveedor = proveedorService.guardar(proveedor);
        return ResponseEntity
            .created(linkTo(methodOn(ProveedorController.class).obtenerPorId(nuevoProveedor.getId())).toUri())
            .body(assembler.toModel(nuevoProveedor));
    }

    @PutMapping("/{id}")
    @Operation(summary="Actualiza un proveedor.", description = "Actualiza los atributos de un proveedor con ID especificada en el URL.")
    public ResponseEntity<EntityModel<Proveedor>> actualizar(@PathVariable Long id, @RequestBody Proveedor proveedorActualizado) {
        Proveedor existente = proveedorService.obtenerPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        proveedorActualizado.setId(id);
        Proveedor actualizado = proveedorService.guardar(proveedorActualizado);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary="Borra un proveedor.",description = "Borra un proveedor con ID especificada en el URL.")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (proveedorService.obtenerPorId(id) == null) {
            return ResponseEntity.notFound().build();
        }
        proveedorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
