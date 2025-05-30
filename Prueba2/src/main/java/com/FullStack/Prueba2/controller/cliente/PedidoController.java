package com.FullStack.Prueba2.controller.cliente;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.FullStack.Prueba2.model.cliente.Pedido;
import com.FullStack.Prueba2.service.cliente.PedidoService;

@RestController
@RequestMapping("/api/pedidos")

public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public List<Pedido> getAllPedidos() {
    return pedidoService.getAllPedidos();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPedidoPorId(@PathVariable Long id) {
    Pedido pedido = pedidoService.getPedidoById(id);
    return ResponseEntity.ok(pedido);
    }

    @PostMapping
    public ResponseEntity<Pedido> agregarPedido(@RequestBody Pedido pedido) {
    Pedido pedidoGuardado = pedidoService.addPedido(pedido);
    return ResponseEntity.status(HttpStatus.CREATED).body(pedidoGuardado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Long id) {
    pedidoService.eliminarPedido(id);
    return ResponseEntity.noContent().build(); 
    }


    @PutMapping("/{id}")
    public ResponseEntity<Pedido> actualizarPedido(@PathVariable Long id, @RequestBody Pedido pedidoActualizado) {
    Pedido pedido = pedidoService.actualizarPedido(id, pedidoActualizado);
    return ResponseEntity.ok(pedido);
    }
}
