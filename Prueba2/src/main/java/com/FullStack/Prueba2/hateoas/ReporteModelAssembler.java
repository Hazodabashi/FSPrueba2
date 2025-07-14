package com.FullStack.Prueba2.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.lang.NonNull;
import com.FullStack.Prueba2.controller.cliente.ReporteController;
import com.FullStack.Prueba2.model.cliente.Reporte;

@Component
public class ReporteModelAssembler implements RepresentationModelAssembler<Reporte, EntityModel<Reporte>> {

    @Override
    public @NonNull EntityModel<Reporte> toModel(Reporte reporte) {
        return EntityModel.of(reporte,
            linkTo(methodOn(ReporteController.class).obtenerReportePorId(reporte.getId())).withSelfRel(),
            linkTo(methodOn(ReporteController.class).getAllReportes()).withRel("reportes")
        );
    }
}
