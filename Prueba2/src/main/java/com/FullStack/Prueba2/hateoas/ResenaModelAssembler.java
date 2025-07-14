package com.FullStack.Prueba2.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.FullStack.Prueba2.controller.cliente.ResenaController;
import com.FullStack.Prueba2.model.cliente.Resena;

@Component
public class ResenaModelAssembler implements RepresentationModelAssembler<Resena, EntityModel<Resena>> {

    @Override
    public @NonNull EntityModel<Resena> toModel(Resena resena) {
        return EntityModel.of(resena,
            linkTo(methodOn(ResenaController.class).obtenerResenaPorId(resena.getIdResena())).withSelfRel(),
            linkTo(methodOn(ResenaController.class).getAllResenas()).withRel("resenas")
        );
    }
}
