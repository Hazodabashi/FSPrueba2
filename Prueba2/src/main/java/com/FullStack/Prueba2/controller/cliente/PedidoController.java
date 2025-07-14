package com.FullStack.Prueba2.controller.cliente;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.FullStack.Prueba2.model.cliente.Pedido;
import com.FullStack.Prueba2.service.cliente.PedidoService;
import com.FullStack.Prueba2.hateoas.PedidoModelAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;

@RestController
@Tag(name = "Pedidos", description = "EndPoints de pedidos")
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Obtiene todos los pedidos listados.", description = "Obtiene todos los pedidos listados.")
    public ResponseEntity<CollectionModel<EntityModel<Pedido>>> getAllPedidos() {
        List<EntityModel<Pedido>> pedidos = pedidoService.getAllPedidos().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        CollectionModel<EntityModel<Pedido>> collectionModel = CollectionModel.of(pedidos,
            linkTo(methodOn(PedidoController.class).getAllPedidos()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

@GetMapping("/{id}")
@Operation(summary = "Obtiene un pedido", description = "Obtiene un pedido en especifico con la ID ingresada en el URL.")
public ResponseEntity<?> obtenerPedidoPorId(@PathVariable Long id) {
    try {
        Pedido pedido = pedidoService.getPedidoById(id);
        return ResponseEntity.ok(assembler.toModel(pedido));
    } catch (EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(EntityModel.of(Map.of(
                "status", "error",
                "message", e.getMessage()
            )));
    }
}


    @PostMapping
    @Operation(summary = "Agrega un pedido.", description = "Agrega un pedido nuevo ingresando los atributos.")
    public ResponseEntity<EntityModel<Pedido>> agregarPedido(@RequestBody Pedido pedido) {
        Pedido pedidoGuardado = pedidoService.addPedido(pedido);
        EntityModel<Pedido> entityModel = assembler.toModel(pedidoGuardado);
        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borra un pedido.", description = "Borra un pedido especifico con la ID ingresada en el URL")
    public ResponseEntity<?> eliminarPedido(@PathVariable Long id) {
        try {
            pedidoService.eliminarPedido(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "status", "error",
                "message", e.getMessage()
            ));
        }
    }

@PutMapping("/{id}")
@Operation(summary = "Actualiza un pedido.", description = "Actualiza un pedido ingresando los atributos a cambiar.")
public ResponseEntity<?> actualizarPedido(@PathVariable Long id, @RequestBody Pedido pedidoActualizado) {
    try {
        Pedido pedido = pedidoService.actualizarPedido(id, pedidoActualizado);
        return ResponseEntity.ok(assembler.toModel(pedido));
    } catch (EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(EntityModel.of(Map.of(
                    "status", "error",
                    "message", e.getMessage()
                )));
    }
}

}
