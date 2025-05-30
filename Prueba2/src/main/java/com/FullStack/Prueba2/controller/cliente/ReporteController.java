package com.FullStack.Prueba2.controller.cliente;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.FullStack.Prueba2.model.cliente.Reporte;
import com.FullStack.Prueba2.service.cliente.ReporteService;

@RestController
@RequestMapping("/api/reportes")

public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping
    public List<Reporte> getAllReportes(){
        return reporteService.getAllReportes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reporte> obtenerReportePorId(@PathVariable Long id) {
    Reporte reporte = reporteService.getReporteById(id);
    return ResponseEntity.ok(reporte);
    }

    @PostMapping
    public ResponseEntity<Reporte> addReporte(@RequestBody Reporte reporte) {
    Reporte guardado = reporteService.addReporte(reporte);
    return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReporte(@PathVariable Long id) {
    reporteService.eliminarReporte(id);
    return ResponseEntity.noContent().build(); // Devuelve 204 No Content
    }

@PutMapping("/{id}")
public ResponseEntity<Reporte> actualizarReporte(@PathVariable Long id, @RequestBody Reporte reporte) {
Reporte actualizado = reporteService.actualizarReporte(id, reporte);
return ResponseEntity.ok(actualizado); // 200 OK con el reporte actualizado en el body
}
}
