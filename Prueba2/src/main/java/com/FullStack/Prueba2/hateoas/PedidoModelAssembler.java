package com.FullStack.Prueba2.hateoas;

import com.FullStack.Prueba2.controller.cliente.PedidoController;
import com.FullStack.Prueba2.model.cliente.Pedido;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PedidoModelAssembler implements RepresentationModelAssembler<Pedido, EntityModel<Pedido>> {

@Override
public @NonNull EntityModel<Pedido> toModel(Pedido pedido) {
    return EntityModel.of(pedido,
        linkTo(methodOn(PedidoController.class).obtenerPedidoPorId(pedido.getIdPedido())).withSelfRel(),
        linkTo(methodOn(PedidoController.class).getAllPedidos()).withRel("pedidos"));
}

}