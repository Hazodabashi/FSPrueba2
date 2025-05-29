package com.FullStack.Prueba2.controller.cliente;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.FullStack.Prueba2.model.cliente.Cliente;

import com.FullStack.Prueba2.repository.cliente.ClienteRepository;
import com.FullStack.Prueba2.service.cliente.ClienteService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/clientes")

public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Cliente> agregarCliente(@RequestBody Cliente cliente) {
    Cliente guardado = clienteService.addCliente(cliente);
    return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
}



    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerClientePorId(@PathVariable Long id) {
    try {
        Cliente cliente = clienteService.getClienteById(id);
        return ResponseEntity.ok(cliente);
    } catch (EntityNotFoundException e) {
        Map<String, String> error = Map.of(
            "status", "error",
            "message", e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
    try {
        clienteService.eliminarCliente(id);
    return ResponseEntity.noContent().build(); // 204 No Content
        } catch (EntityNotFoundException e) {
    return ResponseEntity.notFound().build(); // 404 Not Found
        } catch (Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Error
        }
    }


@PutMapping("/{id}")
public ResponseEntity<Map<String, String>> actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
    try {
        clienteService.updateCliente(id, cliente);
        Map<String, String> response = Map.of(
            "status", "success",
            "message", "Cliente actualizado correctamente"
        );
        return ResponseEntity.ok(response);
    } catch (EntityNotFoundException e) {
        Map<String, String> response = Map.of(
            "status", "error",
            "message", "Cliente no encontrado"
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}

}
