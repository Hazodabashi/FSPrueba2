package com.FullStack.Prueba2.controller.gestioninventario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.FullStack.Prueba2.model.gestionInventario.Proveedor;
import com.FullStack.Prueba2.service.gestioninventario.ProveedorService;

@RestController
@RequestMapping("/api/proveedores")

public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public List<Proveedor> listarTodos() {
        return proveedorService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> obtenerPorId(@PathVariable Long id) {
        Proveedor proveedor = proveedorService.obtenerPorId(id);
        return proveedor != null ? ResponseEntity.ok(proveedor) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Proveedor crear(@RequestBody Proveedor proveedor) {
        return proveedorService.guardar(proveedor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> actualizar(@PathVariable Long id, @RequestBody Proveedor proveedorActualizado) {
        Proveedor existente = proveedorService.obtenerPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        proveedorActualizado.setId(id);
        return ResponseEntity.ok(proveedorService.guardar(proveedorActualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (proveedorService.obtenerPorId(id) == null) {
            return ResponseEntity.notFound().build();
        }
        proveedorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
