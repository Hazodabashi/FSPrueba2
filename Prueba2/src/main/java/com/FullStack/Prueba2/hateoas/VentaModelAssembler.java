package com.FullStack.Prueba2.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.FullStack.Prueba2.controller.venta.VentaController;
import com.FullStack.Prueba2.model.venta.Venta;

@Component
public class VentaModelAssembler implements RepresentationModelAssembler<Venta, EntityModel<Venta>> {

    @Override
    public @NonNull EntityModel<Venta> toModel(Venta venta) {
        return EntityModel.of(venta,
            linkTo(methodOn(VentaController.class).getVentaById(venta.getIdVenta())).withSelfRel(),
            linkTo(methodOn(VentaController.class).getAllVentas()).withRel("ventas")
        );
    }
}
