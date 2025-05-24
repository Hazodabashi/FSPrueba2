package com.FullStack.Prueba2.service.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FullStack.Prueba2.model.cliente.Reporte;
import com.FullStack.Prueba2.repository.cliente.ReporteRepository;

@Service
public class ReporteService {
    @Autowired
    private ReporteRepository reporteRepository;

    //Listar
    public String getAllReportes() {
        String output = "";

        for(Reporte reporte : reporteRepository.findAll()) {
            output +="ID Reporte: "+reporte.getId()+"\n";
            output +="Tipo: "+reporte.getTipo()+"\n";
            output +="Fecha de generacion: "+reporte.getFechaGeneracion()+"\n";
            output +="Datos: "+reporte.getDatos()+"\n";
        }
        if(output.isEmpty()){
            return "No se encontraron reportes";
        }else{
            return output;
        }
    }
    //Buscar
    public String getReporteById(Long id) {
        String output = "";

        if(reporteRepository.existsById(id)) {
            Reporte buscado = reporteRepository.findById(id).get();
            output += "ID Reporte: "+buscado.getId()+"\n";
            output += "Tipo: "+buscado.getTipo()+"\n";
            output += "Fecha de generacion: "+buscado.getFechaGeneracion()+"\n";
            output += "Datos: "+buscado.getDatos()+"\n";
            return output;
        }else{
            return "No se encontraron reportes con esa ID";
        }
    }

    //Agregar
    public String addReporte(Reporte reporte) {
        if(!reporteRepository.existsById(reporte.getId())) {
            reporteRepository.save(reporte);
            return "Reporte agregado correctamente";
        }else{
            return "Reporte ya existente";
        }
    }

    //Eliminar
    public String deleteReporte(Long id) {
        if(reporteRepository.existsById(id)) {
            reporteRepository.deleteById(id);
            return "Reporte eliminado correctamente";
        }else{
            return "No se encontraron reportes con esa ID";
        }
    }

    //Actualizar
    public String updateReporte(Long id, Reporte reporte) {
        if(reporteRepository.existsById(id)) {
            Reporte buscado = reporteRepository.findById(id).get();
            buscado.setTipo(reporte.getTipo());
            buscado.setFechaGeneracion(reporte.getFechaGeneracion());
            buscado.setDatos(reporte.getDatos());
            reporteRepository.save(buscado);
            return "Reporte actualizado correctamente";
        }else{
            return "No se encontraron reportes con esa ID";
        }
    }
}