package com.FullStack.Prueba2.controller.cliente;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.FullStack.Prueba2.model.cliente.Resena;
import com.FullStack.Prueba2.service.cliente.ResenaService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/resenas")
public class ResenaController {

    @Autowired
    private ResenaService resenaService;

    @GetMapping
    public List<Resena> getAllResenas() {
        return resenaService.getAllResenas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resena> obtenerResenaPorId(@PathVariable Long id) {
    try {
        Resena resena = resenaService.getResenaById(id);
        return ResponseEntity.ok(resena);
    } catch (EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    }

    @PostMapping
    public ResponseEntity<Resena> agregarResena(@RequestBody Resena resena) {
        Resena resenaGuardada = resenaService.addResena(resena);
        return ResponseEntity.status(HttpStatus.CREATED).body(resenaGuardada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarResena(@PathVariable Long id) {
        try {
            resenaService.eliminarResena(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resena> actualizarResena(@PathVariable Long id, @RequestBody Resena resenaActualizada) {
        try {
            Resena resena = resenaService.actualizarResena(id, resenaActualizada);
            return ResponseEntity.ok(resena);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
