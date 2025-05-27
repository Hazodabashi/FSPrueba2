package com.FullStack.Prueba2.controller.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.FullStack.Prueba2.model.cliente.Cliente;
import com.FullStack.Prueba2.model.cliente.Pedido;
import com.FullStack.Prueba2.service.cliente.ClienteService;

@RestController
@RequestMapping("/api/clientes")

public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public String getAllClientes() {return clienteService.getAllClientes();}

    @PostMapping
    public String addCliente(@RequestBody Cliente cliente) {return clienteService.addCliente(cliente); }

    @GetMapping("/{id}")
    public String getClienteById(@PathVariable Long id) {return clienteService.getClienteById(id); }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarClienteYAsociados(@PathVariable Long id) {
        String resultado = clienteService.borrarClienteYRelacionados(id);
        if (resultado.contains("no encontrado")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultado);
        }
        return ResponseEntity.ok(resultado);
    }

    @PutMapping("/{id}")
    public String updateCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        return clienteService.updateCliente(id, cliente);
    }
    @PostMapping("/{idCliente}/pedidos")
    public String asignarPedido(@PathVariable Long idCliente, @RequestBody Pedido pedido) {
        return clienteService.asignarPedidoACliente(idCliente, pedido);
    }

}
