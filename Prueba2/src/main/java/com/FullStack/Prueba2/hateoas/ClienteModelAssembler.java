package com.FullStack.Prueba2.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.FullStack.Prueba2.controller.cliente.ClienteController;
import com.FullStack.Prueba2.model.cliente.Cliente;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ClienteModelAssembler implements RepresentationModelAssembler<Cliente, EntityModel<Cliente>> {

    @Override
    public EntityModel<Cliente> toModel(Cliente cliente) {
        return EntityModel.of(cliente,
            linkTo(methodOn(ClienteController.class).obtenerClientePorId(cliente.getIdCliente())).withSelfRel(),
            linkTo(methodOn(ClienteController.class).getAllClientes()).withRel("clientes")
        );
    }
}
