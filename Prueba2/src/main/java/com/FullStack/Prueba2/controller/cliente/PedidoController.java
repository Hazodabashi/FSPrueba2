package com.FullStack.Prueba2.controller.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.FullStack.Prueba2.model.cliente.Pedido;
import com.FullStack.Prueba2.service.cliente.PedidoService;

@RestController
@RequestMapping("/api/pedidos")

public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public String getAllPedidos() {return pedidoService.getAllPedidos(); }

    @PostMapping
    public String addPedido(@RequestBody Pedido pedido) {return pedidoService.addPedido(pedido); }

    @GetMapping("/{id}")
    public String getPedidoById(@PathVariable Long id) {return pedidoService.getPedidoById(id); }

    @DeleteMapping("/{id}")
    public String deletePedido(@PathVariable Long id) {return pedidoService.deletePedido(id); }

    @PutMapping("/{id}")
    public String updatePedido(@PathVariable Long id, @RequestBody Pedido pedido) {
        return pedidoService.updatePedido(id, pedido);
    }
}
