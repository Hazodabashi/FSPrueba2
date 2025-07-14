package com.FullStack.Prueba2.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.FullStack.Prueba2.controller.envio.EnvioController;
import com.FullStack.Prueba2.model.envio.Envio;

@Component
public class EnvioModelAssembler implements RepresentationModelAssembler<Envio, EntityModel<Envio>> {

    @Override
    public @NonNull EntityModel<Envio> toModel(Envio envio) {
        return EntityModel.of(envio,
            linkTo(methodOn(EnvioController.class).getEnvioById(envio.getIdEnvio())).withSelfRel(),
            linkTo(methodOn(EnvioController.class).getAllEnvios()).withRel("envios")
        );
    }
}
