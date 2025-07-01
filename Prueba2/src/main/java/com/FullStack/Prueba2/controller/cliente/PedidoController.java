package com.FullStack.Prueba2.controller.cliente;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.FullStack.Prueba2.model.cliente.Pedido;
import com.FullStack.Prueba2.service.cliente.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;

@RestController
@Tag(name = "Pedidos", description = "EndPoints de pedidos")
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    @Operation(summary="Obtiene todos los pedidos listados.",description = "Obtiene todos los pedidos listados.")
    public List<Pedido> getAllPedidos() {
        return pedidoService.getAllPedidos();
    }

    @GetMapping("/{id}")
    @Operation(summary="Obtiene un pedido",description = "Obtiene un pedido en especifico con la ID ingresada en el URL.")
    public ResponseEntity<?> obtenerPedidoPorId(@PathVariable Long id) {
        try {
            Pedido pedido = pedidoService.getPedidoById(id);
            return ResponseEntity.ok(pedido);
        } catch (EntityNotFoundException e) {
            Map<String, String> error = Map.of(
                "status", "error",
                "message", e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @PostMapping
    @Operation(summary="Agrega un pedido.",description = "Agrega un pedido nuevo ingresando los atrivutos.")
    public ResponseEntity<Pedido> agregarPedido(@RequestBody Pedido pedido) {
        Pedido pedidoGuardado = pedidoService.addPedido(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoGuardado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary="Borra un pedido.",description = "Borra un pedido especifico con la ID ingresada en el URL")
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
    @Operation(summary="Actualiza un pedido.",description = "Actualiza un pedido ingresando los atributos a cambiar.")
    public ResponseEntity<?> actualizarPedido(@PathVariable Long id, @RequestBody Pedido pedidoActualizado) {
        try {
            Pedido pedido = pedidoService.actualizarPedido(id, pedidoActualizado);
            return ResponseEntity.ok(pedido);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "status", "error",
                "message", e.getMessage()
            ));
        }
    }
}
