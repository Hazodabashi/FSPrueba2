package com.FullStack.Prueba2.controller.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.FullStack.Prueba2.model.cliente.Cliente;
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
    public String deleteCliente(@PathVariable Long id) {return clienteService.deleteCliente(id); }

    @PutMapping("/{id}")
    public String updateCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        return clienteService.updateCliente(id, cliente);
    }
}
