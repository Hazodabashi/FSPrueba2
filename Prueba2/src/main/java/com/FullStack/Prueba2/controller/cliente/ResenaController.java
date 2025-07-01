package com.FullStack.Prueba2.controller.cliente;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    @Operation(summary="Lista todas las reseñas.",description = "Lista todas las reseñas que estan registradas.")
    public List<Resena> getAllResenas() {
        return resenaService.getAllResenas();
    }

    @GetMapping("/{id}")
    @Operation(summary="Lista una reseña.",description = "Lista la reseña con la ID especificada en la URL.")
    public ResponseEntity<Resena> obtenerResenaPorId(@PathVariable Long id) {
    try {
        Resena resena = resenaService.getResenaById(id);
        return ResponseEntity.ok(resena);
    } catch (EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    }

    @PostMapping
    @Operation(summary="Registra una reseña.",description = "Registra una nueve reseña con los atributos ingresados.")
    public ResponseEntity<Resena> agregarResena(@RequestBody Resena resena) {
        Resena resenaGuardada = resenaService.addResena(resena);
        return ResponseEntity.status(HttpStatus.CREATED).body(resenaGuardada);
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
    public ResponseEntity<Resena> actualizarResena(@PathVariable Long id, @RequestBody Resena resenaActualizada) {
        try {
            Resena resena = resenaService.actualizarResena(id, resenaActualizada);
            return ResponseEntity.ok(resena);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
