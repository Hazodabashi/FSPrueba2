package com.FullStack.Prueba2.controller.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.FullStack.Prueba2.model.cliente.Reporte;
import com.FullStack.Prueba2.service.cliente.ReporteService;

@RestController
@RequestMapping("/api/reportes")

public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping
    public String getAllReportes() {return reporteService.getAllReportes(); }

    @PostMapping
    public String addReporte(@RequestBody Reporte reporte) {return reporteService.addReporte(reporte); }

    @GetMapping("/{id}")
    public String getReporteById(@PathVariable Long id) {return reporteService.getReporteById(id); }

    @DeleteMapping("/{id}")
    public String deleteReporte(@PathVariable Long id) {return reporteService.deleteReporte(id); }

    @PutMapping("/{id}")
    public String updateReporte(@PathVariable Long id, @RequestBody Reporte reporte) {
        return reporteService.updateReporte(id, reporte);
    }
}
