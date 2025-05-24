package com.FullStack.Prueba2.controller.gestioninventario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.FullStack.Prueba2.model.gestionInventario.Proveedor;
import com.FullStack.Prueba2.service.gestioninventario.ProveedorService;

@RestController
@RequestMapping("/api/proveedores")

public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public String getAllProveedores() { return proveedorService.getAllProveedores(); }

    @PostMapping
    public String addProveedor(@RequestBody Proveedor proveedor) { return proveedorService.addProveedor(proveedor);}

    @GetMapping("/{id}")
    public String getProveedor(@PathVariable Long id) { return proveedorService.getProveedorById(id); }

    @DeleteMapping("/{id}")
    public String deleteProveedor(@PathVariable Long id) {return proveedorService.deleteProveedor(id); }

    @PutMapping("/{id}")
    public String updateProveedor(@PathVariable Long id, @RequestBody Proveedor proveedor) {
        return proveedorService.updateProveedor(id,proveedor);
    }

}
