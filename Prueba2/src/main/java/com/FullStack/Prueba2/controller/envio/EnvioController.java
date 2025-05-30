package com.FullStack.Prueba2.controller.envio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.FullStack.Prueba2.model.envio.Envio;
import com.FullStack.Prueba2.service.envio.EnvioService;

@RestController
@RequestMapping("/api/envios")

public class EnvioController {

    @Autowired
    private EnvioService envioService;

    @GetMapping
    public ResponseEntity<List<Envio>> getAllEnvios() {
    return ResponseEntity.ok(envioService.getAllEnvios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Envio> getEnvioById(@PathVariable Long id) {
    return ResponseEntity.ok(envioService.getEnvioById(id));
    }

    @PostMapping
    public ResponseEntity<Envio> addEnvio(@RequestBody Envio envio) {
    Envio creado = envioService.addEnvio(envio);
    return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEnvio(@PathVariable Long id) {
    envioService.eliminarEnvio(id);
    return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Envio> actualizarEnvio(@PathVariable Long id, @RequestBody Envio envio) {
    Envio actualizado = envioService.actualizarEnvio(id, envio);
    return ResponseEntity.ok(actualizado);
    }
}