package com.FullStack.Prueba2.controller.venta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.FullStack.Prueba2.model.venta.Venta;
import com.FullStack.Prueba2.service.venta.VentaService;

@RestController
@RequestMapping("/api/ventas")

public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public String getAllVentas() {return ventaService.getAllVentas(); }

    @PostMapping
    public String addVenta(@RequestBody Venta venta) {return ventaService.addVenta(venta); }

    @GetMapping("/{id}")
    public String getVentaById(@PathVariable Long id) {return ventaService.getVentaById(id); }

    @DeleteMapping("/{id}")
    public String deleteVenta(@PathVariable Long id) {return ventaService.deleteVenta(id); }

    @PutMapping("/{id}")
    public String updateVenta(@PathVariable Long id, @RequestBody Venta venta){
        return ventaService.updateVenta(id, venta);
    }
}
