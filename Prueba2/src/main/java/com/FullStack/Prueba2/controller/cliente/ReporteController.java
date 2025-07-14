package com.FullStack.Prueba2.controller.cliente;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.FullStack.Prueba2.hateoas.ReporteModelAssembler;
import com.FullStack.Prueba2.model.cliente.Reporte;
import com.FullStack.Prueba2.service.cliente.ReporteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;

@RestController
@Tag(name = "Reportes", description = "EndPoints de reportes")
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private ReporteModelAssembler assembler;

    @GetMapping
    @Operation(summary="Obtiene todos los reportes",description = "Llama a los reportes y devuelve todos los que estan registrados")
    public CollectionModel<EntityModel<Reporte>> getAllReportes() {
        List<EntityModel<Reporte>> reportes = reporteService.getAllReportes().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(reportes,
            linkTo(methodOn(ReporteController.class).getAllReportes()).withSelfRel());
    }

    @GetMapping("/{id}")
    @Operation(summary="Obtiene un reporte",description = "Obtiene el reporte con ID especifica que es ingresada en la URL")
    public ResponseEntity<EntityModel<Reporte>> obtenerReportePorId(@PathVariable Long id) {
        try {
            Reporte reporte = reporteService.getReporteById(id);
            return ResponseEntity.ok(assembler.toModel(reporte));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    @Operation(summary="Registra un nuevo reporte.",description = "Registra un nuevo reporte con los atributos ingresados.")
    public ResponseEntity<EntityModel<Reporte>> addReporte(@RequestBody Reporte reporte) {
        Reporte guardado = reporteService.addReporte(reporte);
        return ResponseEntity
            .created(linkTo(methodOn(ReporteController.class).obtenerReportePorId(guardado.getId())).toUri())
            .body(assembler.toModel(guardado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary="Borra un reporte.",description = "Borra el reporte con ID especifica que es ingresado en la URL.")
    public ResponseEntity<?> eliminarReporte(@PathVariable Long id) {
        try {
            reporteService.eliminarReporte(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary="Actualiza un reporte.",description = "Actualiza los atributos de un reporte ingresados.")
    public ResponseEntity<EntityModel<Reporte>> actualizarReporte(@PathVariable Long id, @RequestBody Reporte reporte) {
        try {
            Reporte actualizado = reporteService.actualizarReporte(id, reporte);
            return ResponseEntity.ok(assembler.toModel(actualizado));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
