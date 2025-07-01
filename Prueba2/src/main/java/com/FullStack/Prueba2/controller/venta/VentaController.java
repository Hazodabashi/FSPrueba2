package com.FullStack.Prueba2.controller.venta;

import com.FullStack.Prueba2.model.venta.Venta;
import com.FullStack.Prueba2.service.venta.VentaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Ventas", description = "EndPoints de ventas")
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    @Operation(summary = "Obtener todas las ventas.", description= "Devuelve todas las ventas registradas.")
    public ResponseEntity<List<Venta>> getAllVentas() {
        return ResponseEntity.ok(ventaService.getAllVentas());
    }

    @GetMapping("/{id}")
    @Operation(summary="Obtiene una venta",description = "Obtiene una venta en especifico con la ID ingresada en el URL.")
    public ResponseEntity<Venta> getVentaById(@PathVariable Long id) {
        return ResponseEntity.ok(ventaService.getVentaById(id));
    }

    @PostMapping
    @Operation(summary="Registra una venta nueva.",description = "Registra una venta nueva junto con todos sus atributos")
    public ResponseEntity<Venta> addVenta(@RequestBody Venta venta) {
        Venta nuevaVenta = ventaService.addVenta(venta);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVenta);
    }

    @DeleteMapping("/{id}")
    @Operation(summary="Borra una venta",description = "Borra la venta con la ID ingresada en la URL")
    public ResponseEntity<Void> deleteVenta(@PathVariable Long id) {
        ventaService.eliminarVenta(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary="Actualiza una venta",description = "Actualiza una venta con los atributos ingresados")
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
