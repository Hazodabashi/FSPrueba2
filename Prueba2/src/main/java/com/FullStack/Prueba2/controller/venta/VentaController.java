package com.FullStack.Prueba2.controller.venta;

import com.FullStack.Prueba2.model.venta.Venta;
import com.FullStack.Prueba2.service.venta.VentaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<Venta>> getAllVentas() {
        return ResponseEntity.ok(ventaService.getAllVentas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venta> getVentaById(@PathVariable Long id) {
        return ResponseEntity.ok(ventaService.getVentaById(id));
    }

    @PostMapping
    public ResponseEntity<Venta> addVenta(@RequestBody Venta venta) {
        Venta nuevaVenta = ventaService.addVenta(venta);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVenta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenta(@PathVariable Long id) {
        ventaService.eliminarVenta(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Venta> updateVenta(@PathVariable Long id, @RequestBody Venta venta) {
        Venta actualizada = ventaService.actualizarVenta(id, venta);
        return ResponseEntity.ok(actualizada);
    }

    // Manejo de excepción para EntityNotFoundException -> 404 NOT FOUND
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
