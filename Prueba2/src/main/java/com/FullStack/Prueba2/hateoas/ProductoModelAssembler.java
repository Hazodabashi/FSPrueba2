package com.FullStack.Prueba2.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.FullStack.Prueba2.controller.gestioninventario.ProductoController;
import com.FullStack.Prueba2.model.gestionInventario.Producto;

@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {

    @Override
    public @NonNull EntityModel<Producto> toModel(Producto producto) {
        return EntityModel.of(producto,
            linkTo(methodOn(ProductoController.class).getProductoById(producto.getIdProducto())).withSelfRel(),
            linkTo(methodOn(ProductoController.class).getAllProductos()).withRel("productos")
        );
    }
}
