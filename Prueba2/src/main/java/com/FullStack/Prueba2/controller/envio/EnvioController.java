package com.FullStack.Prueba2.controller.envio;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.FullStack.Prueba2.model.envio.Envio;
import com.FullStack.Prueba2.service.envio.EnvioService;
import com.FullStack.Prueba2.hateoas.EnvioModelAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;

@RestController
@Tag(name = "Envios", description = "EndPoints de envios")
@RequestMapping("/api/envios")
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    @Autowired
    private EnvioModelAssembler assembler;

    @GetMapping
    @Operation(summary="Lista envios.",description = "Lista todos los envios registrados.")
    public ResponseEntity<CollectionModel<EntityModel<Envio>>> getAllEnvios() {
        List<EntityModel<Envio>> envios = envioService.getAllEnvios().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return ResponseEntity.ok(
            CollectionModel.of(envios,
                linkTo(methodOn(EnvioController.class).getAllEnvios()).withSelfRel())
        );
    }

    @GetMapping("/{id}")
    @Operation(summary="Lista un envio",description = "Lista el envio con ID especificada en la URL")
    public ResponseEntity<EntityModel<Envio>> getEnvioById(@PathVariable Long id) {
        Envio envio = envioService.getEnvioById(id);
        return ResponseEntity.ok(assembler.toModel(envio));
    }

    @PostMapping
    @Operation(summary="Registra envio.",description = "Registra un nuevo envio.")
    public ResponseEntity<EntityModel<Envio>> addEnvio(@RequestBody Envio envio) {
        Envio creado = envioService.addEnvio(envio);
        return ResponseEntity
            .created(linkTo(methodOn(EnvioController.class).getEnvioById(creado.getIdEnvio())).toUri())
            .body(assembler.toModel(creado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary="Borra un envio.",description = "Borra el envio con ID especificada en el URL.")
    public ResponseEntity<Void> eliminarEnvio(@PathVariable Long id) {
        envioService.eliminarEnvio(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary="Actualiza el envio.",description = "Actualiza los atributos del envio con ID especificada en el URL.")
    public ResponseEntity<EntityModel<Envio>> actualizarEnvio(@PathVariable Long id, @RequestBody Envio envio) {
        Envio actualizado = envioService.actualizarEnvio(id, envio);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
