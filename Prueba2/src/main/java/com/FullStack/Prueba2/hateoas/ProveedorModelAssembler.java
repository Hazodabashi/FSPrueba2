package com.FullStack.Prueba2.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.FullStack.Prueba2.controller.gestioninventario.ProveedorController;
import com.FullStack.Prueba2.model.gestionInventario.Proveedor;

@Component
public class ProveedorModelAssembler implements RepresentationModelAssembler<Proveedor, EntityModel<Proveedor>> {

    @Override
    public @NonNull EntityModel<Proveedor> toModel(Proveedor proveedor) {
        return EntityModel.of(proveedor,
            linkTo(methodOn(ProveedorController.class).obtenerPorId(proveedor.getId())).withSelfRel(),
            linkTo(methodOn(ProveedorController.class).listarTodos()).withRel("proveedores")
        );
    }
}
