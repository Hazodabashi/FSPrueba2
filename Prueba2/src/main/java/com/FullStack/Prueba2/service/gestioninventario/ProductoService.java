package com.FullStack.Prueba2.service.gestioninventario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FullStack.Prueba2.model.gestionInventario.Producto;
import com.FullStack.Prueba2.model.gestionInventario.Proveedor;
import com.FullStack.Prueba2.repository.gestioninventario.ProductoRepository;
import com.FullStack.Prueba2.repository.gestioninventario.ProveedorRepository;

import java.util.List;
import java.util.Random;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    // Listar:
    public String getAllProductos() {
        String output = "";

        for (Producto producto : productoRepository.findAll()) {
            output += "ID Producto: " + producto.getIdProducto() + "\n";
            output += "Stock: " + producto.getStock() + "\n";
            output += "ID Proveedor: " + (producto.getProveedor() != null ? producto.getProveedor().getId() : "Sin proveedor") + "\n";
            output += "Nombre: " + producto.getNombre() + "\n";
            output += "Descripcion: " + producto.getDescripcion() + "\n";
            output += "Categoria: " + producto.getCategoria() + "\n";
            output += "Precio: " + producto.getPrecio() + "\n";
            output += "\n";
            output += "------------------o------------------";
            output += "\n";
        }
        if (output.isEmpty()) {
            return "No se encontraron productos";
        } else {
            return output;
        }
    }

    // Buscar
    public String getProductoById(Long id) {
        String output = "";
        if (productoRepository.existsById(id)) {
            Producto buscado = productoRepository.findById(id).get();
            output += "ID Producto: " + buscado.getIdProducto() + "\n";
            output += "Stock: " + buscado.getStock() + "\n";
            output += "ID Proveedor: " + (buscado.getProveedor() != null ? buscado.getProveedor().getId() : "Sin proveedor") + "\n";
            output += "Nombre: " + buscado.getNombre() + "\n";
            output += "Descripcion: " + buscado.getDescripcion() + "\n";
            output += "Categoria: " + buscado.getCategoria() + "\n";
            output += "Precio: " + buscado.getPrecio() + "\n";
            return output;
        } else {
            return "No se encontraron productos con esa ID";
        }
    }

    // Agregar con proveedor automático
public String addProducto(Producto producto) {
    List<Proveedor> proveedores = proveedorRepository.findAll();
    if (proveedores.isEmpty()) {
        return "No hay proveedores disponibles para asignar al producto";
    }
    // Elegir proveedor random
    int randomIndex = new Random().nextInt(proveedores.size());
    Proveedor proveedorRandom = proveedores.get(randomIndex);

    producto.setProveedor(proveedorRandom);
    productoRepository.save(producto);
    return "Producto agregado correctamente con proveedor ID: " + producto.getProveedor().getId();
}

    // Eliminar
    public String deleteProducto(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return "Producto eliminado correctamente";
        } else {
            return "No se encontraron productos con esa ID";
        }
    }

    // Actualizar
    public String updateProducto(Long id, Producto producto) {
        if (productoRepository.existsById(id)) {
            Producto buscado = productoRepository.findById(id).get();
            buscado.setStock(producto.getStock());
            buscado.setProveedor(producto.getProveedor());
            buscado.setNombre(producto.getNombre());
            buscado.setDescripcion(producto.getDescripcion());
            buscado.setCategoria(producto.getCategoria());
            buscado.setPrecio(producto.getPrecio());
            productoRepository.save(buscado);
            return "Producto actualizado correctamente";
        } else {
            return "No se encontraron productos con esa ID";
        }
    }

}
