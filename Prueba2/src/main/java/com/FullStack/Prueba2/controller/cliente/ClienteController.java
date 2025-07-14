package com.FullStack.Prueba2.controller.cliente;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.FullStack.Prueba2.model.cliente.Cliente;
import com.FullStack.Prueba2.service.cliente.ClienteService;
import com.FullStack.Prueba2.hateoas.ClienteModelAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;

@RestController
@Tag(name = "Clientes", description = "EndPoints de clientes")
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Obtener todos los clientes.", description= "Devuelve todos los clientes registrados.")
    public CollectionModel<EntityModel<Cliente>> getAllClientes() {
        List<EntityModel<Cliente>> clientes = clienteService.getAllClientes().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(clientes,
            linkTo(methodOn(ClienteController.class).getAllClientes()).withSelfRel()
        );
    }

    @PostMapping
    @Operation(summary="Registra un cliente nuevo.",description = "Registra un cliente nuevo junto con todos sus atributos")
    public ResponseEntity<EntityModel<Cliente>> agregarCliente(@RequestBody Cliente cliente) {
        Cliente guardado = clienteService.addCliente(cliente);
        return ResponseEntity
            .created(linkTo(methodOn(ClienteController.class).obtenerClientePorId(guardado.getIdCliente())).toUri())
            .body(assembler.toModel(guardado));
    }

    @GetMapping("/{id}")
    @Operation(summary="Obtiene el Cliente",description = "Obtiene el cliente con la ID ingresada en la URL")
    public ResponseEntity<?> obtenerClientePorId(@PathVariable Long id) {
        try {
            Cliente cliente = clienteService.getClienteById(id);
            return ResponseEntity.ok(assembler.toModel(cliente));
        } catch (EntityNotFoundException e) {
            Map<String, String> error = Map.of(
                "status", "error",
                "message", e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

@DeleteMapping("/{id}")
@Operation(summary="Borra el cliente", description = "Borra el cliente con la ID ingresada en la URL")
public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
    try {
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();  // 204 No Content, sin body
    } catch (EntityNotFoundException e) {
        return ResponseEntity.notFound().build();
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}



@PutMapping("/{id}")
@Operation(summary="Actualiza el cliente",description = "Actualiza el Cliente con los atributos ingresados")
public ResponseEntity<?> actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
    try {
        clienteService.updateCliente(id, cliente);
        Cliente actualizado = clienteService.getClienteById(id);
        EntityModel<Cliente> clienteModel = assembler.toModel(actualizado);
        return ResponseEntity.ok(clienteModel);
    } catch (EntityNotFoundException e) {
        Map<String, String> response = Map.of(
            "status", "error",
            "message", "Cliente no encontrado"
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}

}
