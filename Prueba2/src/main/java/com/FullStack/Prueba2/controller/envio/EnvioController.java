package com.FullStack.Prueba2.controller.envio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.FullStack.Prueba2.model.envio.Envio;
import com.FullStack.Prueba2.service.envio.EnvioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;

@RestController
@Tag(name = "Envios", description = "EndPoints de envios")
@RequestMapping("/api/envios")
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    @GetMapping
    @Operation(summary="Lista envios.",description = "Lista todos los envios registrados.")
    public ResponseEntity<List<Envio>> getAllEnvios() {
        return ResponseEntity.ok(envioService.getAllEnvios());
    }

    @GetMapping("/{id}")
    @Operation(summary="Lista un envio",description = "Lista el envio con ID especificada en la URL")
    public ResponseEntity<Envio> getEnvioById(@PathVariable Long id) {
        Envio envio = envioService.getEnvioById(id);
        return ResponseEntity.ok(envio);
    }

    @PostMapping
    @Operation(summary="Registra envio.",description = "Registra un nuevo envio.")
    public ResponseEntity<Envio> addEnvio(@RequestBody Envio envio) {
        Envio creado = envioService.addEnvio(envio);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary="Borra un envio.",description = "Borra el envio con ID especificada en el URL.")
    public ResponseEntity<Void> eliminarEnvio(@PathVariable Long id) {
        envioService.eliminarEnvio(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary="Actualiza el envio.",description = "Actualiza los atributos del envio con ID especificada en el URL.")
    public ResponseEntity<Envio> actualizarEnvio(@PathVariable Long id, @RequestBody Envio envio) {
        Envio actualizado = envioService.actualizarEnvio(id, envio);
        return ResponseEntity.ok(actualizado);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
