package com.FullStack.Prueba2.controller.venta;

import com.FullStack.Prueba2.model.venta.Venta;
import com.FullStack.Prueba2.service.venta.VentaService;
import com.FullStack.Prueba2.hateoas.VentaModelAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Ventas", description = "EndPoints de ventas")
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @Autowired
    private VentaModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Obtener todas las ventas.", description= "Devuelve todas las ventas registradas.")
    public ResponseEntity<CollectionModel<EntityModel<Venta>>> getAllVentas() {
        List<EntityModel<Venta>> ventas = ventaService.getAllVentas().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return ResponseEntity.ok(
            CollectionModel.of(ventas,
                linkTo(methodOn(VentaController.class).getAllVentas()).withSelfRel())
        );
    }

    @GetMapping("/{id}")
    @Operation(summary="Obtiene una venta",description = "Obtiene una venta en especifico con la ID ingresada en el URL.")
    public ResponseEntity<EntityModel<Venta>> getVentaById(@PathVariable Long id) {
        Venta venta = ventaService.getVentaById(id);
        return ResponseEntity.ok(assembler.toModel(venta));
    }

    @PostMapping
    @Operation(summary="Registra una venta nueva.",description = "Registra una venta nueva junto con todos sus atributos")
    public ResponseEntity<EntityModel<Venta>> addVenta(@RequestBody Venta venta) {
        Venta nuevaVenta = ventaService.addVenta(venta);
        return ResponseEntity
            .created(linkTo(methodOn(VentaController.class).getVentaById(nuevaVenta.getIdVenta())).toUri())
            .body(assembler.toModel(nuevaVenta));
    }

    @DeleteMapping("/{id}")
    @Operation(summary="Borra una venta",description = "Borra la venta con la ID ingresada en la URL")
    public ResponseEntity<Void> deleteVenta(@PathVariable Long id) {
        ventaService.eliminarVenta(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary="Actualiza una venta",description = "Actualiza una venta con los atributos ingresados")
    public ResponseEntity<EntityModel<Venta>> updateVenta(@PathVariable Long id, @RequestBody Venta venta) {
        Venta actualizada = ventaService.actualizarVenta(id, venta);
        return ResponseEntity.ok(assembler.toModel(actualizada));
    }

    // Manejo de excepción para EntityNotFoundException -> 404 NOT FOUND
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
