package com.FullStack.Prueba2.controller.gestioninventario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.FullStack.Prueba2.model.gestionInventario.Proveedor;
import com.FullStack.Prueba2.service.gestioninventario.ProveedorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Proveedores", description = "EndPoints de proveedores.")
@RequestMapping("/api/proveedores")

public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    @Operation(summary="Lista todos los proveedores.", description = "Lista todos los proveedores")
    public List<Proveedor> listarTodos() {
        return proveedorService.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary="Lista un proveedor.", description = "Lista un proveedor con la ID especificada en el URL.")
    public ResponseEntity<Proveedor> obtenerPorId(@PathVariable Long id) {
        Proveedor proveedor = proveedorService.obtenerPorId(id);
        return proveedor != null ? ResponseEntity.ok(proveedor) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary="Registra un nuevo proveedor.", description = "Registra un nuevo proveedor con los atributos ingresados.")
    public Proveedor crear(@RequestBody Proveedor proveedor) {
        return proveedorService.guardar(proveedor);
    }

    @PutMapping("/{id}")
    @Operation(summary="Actualiza un proveedor.", description = "Actualiza los atributos de un proveedor con ID especificada en el URL.")
    public ResponseEntity<Proveedor> actualizar(@PathVariable Long id, @RequestBody Proveedor proveedorActualizado) {
        Proveedor existente = proveedorService.obtenerPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        proveedorActualizado.setId(id);
        return ResponseEntity.ok(proveedorService.guardar(proveedorActualizado));
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
