package com.FullStack.Prueba2.service.gestioninventario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.FullStack.Prueba2.model.gestionInventario.Producto;
import com.FullStack.Prueba2.model.gestionInventario.Proveedor;
import com.FullStack.Prueba2.repository.gestioninventario.ProductoRepository;
import com.FullStack.Prueba2.repository.gestioninventario.ProveedorRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;


@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    // Listar:
    public List<Producto> getAllProductos() {
    return productoRepository.findAll();
    }

    // Buscar
    public Producto getProductoById(Long idProducto) {
    return productoRepository.findById(idProducto)
            .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con ID: " + idProducto));
    }


    // Agregar con proveedor automático
    public Producto addProducto(Producto producto) {
    List<Proveedor> proveedores = proveedorRepository.findAll();
    if (proveedores.isEmpty()) {
        throw new IllegalStateException("No hay proveedores disponibles");
    }
    int randomIndex = (int) (Math.random() * proveedores.size());
    Proveedor proveedorAleatorio = proveedores.get(randomIndex);
    producto.setProveedor(proveedorAleatorio);
    return productoRepository.save(producto);
    }


    // Eliminar
    public void eliminarProducto(Long idProducto) {
    if (!productoRepository.existsById(idProducto)) {
        throw new EntityNotFoundException("Producto no encontrado con ID: " + idProducto);
    }
    productoRepository.deleteById(idProducto);
    }

    // Actualizar
    public Producto actualizarProducto(Long id, Producto productoActualizado) {
    Producto productoExistente = productoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con ID: " + id));
    productoExistente.setStock(productoActualizado.getStock());
    productoExistente.setNombre(productoActualizado.getNombre());
    productoExistente.setDescripcion(productoActualizado.getDescripcion());
    productoExistente.setCategoria(productoActualizado.getCategoria());
    productoExistente.setPrecio(productoActualizado.getPrecio());

    return productoRepository.save(productoExistente);
    }


}
