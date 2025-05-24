package com.FullStack.Prueba2.controller.gestioninventario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.FullStack.Prueba2.model.gestionInventario.Producto;
import com.FullStack.Prueba2.service.gestioninventario.ProductoService;

@RestController
@RequestMapping("/api/productos")

public class ProductoController{

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public String getAllProductos() {return productoService.getAllProductos(); }

    @PostMapping
    public String addProducto(@RequestBody Producto producto) {return productoService.addProducto(producto); }

    @GetMapping("/{id}")
    public String getProductoById(@PathVariable Long id) { return productoService.getProductoById(id); }

    @DeleteMapping("/{id}")
    public String deleteProducto(@PathVariable Long id) { return productoService.deleteProducto(id); }

    @PutMapping("/{id}")
    public String updateProducto(@PathVariable Long id, @RequestBody Producto producto){
        return productoService.updateProducto(id,producto);
    }


}
