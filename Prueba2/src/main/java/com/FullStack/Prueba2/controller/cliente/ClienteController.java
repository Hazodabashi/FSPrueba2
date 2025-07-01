package com.FullStack.Prueba2.controller.cliente;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.FullStack.Prueba2.model.cliente.Cliente;

import com.FullStack.Prueba2.service.cliente.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;

@RestController
@Tag(name = "Clientes", description = "EndPoints de clientes")
@RequestMapping("/api/clientes")

public class ClienteController {


    @Autowired
    private ClienteService clienteService;

    @GetMapping
    @Operation(summary = "Obtener todos los clientes.", description= "Devuelve todos los clientes registrados.")
    public List<Cliente> getAllClientes() {
        return clienteService.getAllClientes();
    }

    @PostMapping
    @Operation(summary="Registra un cliente nuevo.",description = "Registra un cliente nuevo junto con todos sus atributos")
    public ResponseEntity<Cliente> agregarCliente(@RequestBody Cliente cliente) {
    Cliente guardado = clienteService.addCliente(cliente);
    return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
}



    @GetMapping("/{id}")
    @Operation(summary="Obtiene el Cliente",description = "Obtiene el cliente con la ID ingresada en la URL")
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
    @Operation(summary="Borra el cliente",description = "Borra el cliente con la ID ingresada en la URL")
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
    @Operation(summary="Actualiza el cliente",description = "Actualiza el Cliente con los atributos ingresados")
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