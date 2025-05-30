package com.FullStack.Prueba2.service.cliente;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.FullStack.Prueba2.model.cliente.Reporte;
import com.FullStack.Prueba2.repository.cliente.ReporteRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ReporteService {
    @Autowired
    private ReporteRepository reporteRepository;

    //Listar
    public List<Reporte> getAllReportes() {
    return reporteRepository.findAll();
    }

    
    //Buscar
    public Reporte getReporteById(Long id) {
    return reporteRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Reporte no encontrado con ID: " + id));
    }


    //Agregar
    public Reporte addReporte(Reporte reporte) {
    return reporteRepository.save(reporte);
    }


    //Eliminar
    public void eliminarReporte(Long id) {
    if (!reporteRepository.existsById(id)) {
        throw new EntityNotFoundException("Reporte no encontrado con ID: " + id);
    }
    reporteRepository.deleteById(id);
    }

    //Actualizar
    public Reporte actualizarReporte(Long id, Reporte reporte) {
        Reporte reporteExistente = reporteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reporte no encontrado con ID: " + id));

        reporteExistente.setTipo(reporte.getTipo());
        reporteExistente.setFechaGeneracion(reporte.getFechaGeneracion());
        reporteExistente.setDatos(reporte.getDatos());

        return reporteRepository.save(reporteExistente);
    }
}