package com.FullStack.Prueba2.controller.cliente;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.FullStack.Prueba2.hateoas.ResenaModelAssembler;
import com.FullStack.Prueba2.model.cliente.Resena;
import com.FullStack.Prueba2.service.cliente.ResenaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;

@RestController
@Tag(name = "Reseñas", description = "EndPoints de reseñas")
@RequestMapping("/api/resenas")
public class ResenaController {

    @Autowired
    private ResenaService resenaService;

    @Autowired
    private ResenaModelAssembler assembler;

    @GetMapping
    @Operation(summary="Lista todas las reseñas.",description = "Lista todas las reseñas que estan registradas.")
    public CollectionModel<EntityModel<Resena>> getAllResenas() {
        List<EntityModel<Resena>> resenas = resenaService.getAllResenas().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(resenas,
            linkTo(methodOn(ResenaController.class).getAllResenas()).withSelfRel());
    }

    @GetMapping("/{id}")
    @Operation(summary="Lista una reseña.",description = "Lista la reseña con la ID especificada en la URL.")
    public ResponseEntity<EntityModel<Resena>> obtenerResenaPorId(@PathVariable Long id) {
        try {
            Resena resena = resenaService.getResenaById(id);
            return ResponseEntity.ok(assembler.toModel(resena));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    @Operation(summary="Registra una reseña.",description = "Registra una nueve reseña con los atributos ingresados.")
    public ResponseEntity<EntityModel<Resena>> agregarResena(@RequestBody Resena resena) {
        Resena resenaGuardada = resenaService.addResena(resena);
        return ResponseEntity
            .created(linkTo(methodOn(ResenaController.class).obtenerResenaPorId(resenaGuardada.getIdResena())).toUri())
            .body(assembler.toModel(resenaGuardada));
    }

    @DeleteMapping("/{id}")
    @Operation(summary="Borra una reseña.",description = "Borra la reseña con ID especificada en la URL.")
    public ResponseEntity<Void> eliminarResena(@PathVariable Long id) {
        try {
            resenaService.eliminarResena(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary="Actualiza una reseña.",description = "Actualiza los atributos de la reseña especificada en la URL.")
    public ResponseEntity<EntityModel<Resena>> actualizarResena(@PathVariable Long id, @RequestBody Resena resenaActualizada) {
        try {
            Resena resena = resenaService.actualizarResena(id, resenaActualizada);
            return ResponseEntity.ok(assembler.toModel(resena));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
