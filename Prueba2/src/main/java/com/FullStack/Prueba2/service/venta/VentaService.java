package com.FullStack.Prueba2.service.venta;

import com.FullStack.Prueba2.model.cliente.Pedido;
import com.FullStack.Prueba2.model.venta.Venta;
import com.FullStack.Prueba2.repository.cliente.PedidoRepository;
import com.FullStack.Prueba2.repository.venta.VentaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private PedidoRepository pedidoRepository;

    // Listar todas las ventas
    public List<Venta> getAllVentas() {
    return ventaRepository.findAll();
    }

    // Buscar una venta por ID
    public Venta getVentaById(Long id) {
    return ventaRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Venta no encontrada con ID: " + id));
    }



public Venta addVenta(Venta venta) {
    // Obtener el pedido asociado desde BD con productos cargados
    Pedido pedido = pedidoRepository.findById(venta.getPedido().getIdPedido())
                    .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con ID: " + venta.getPedido().getIdPedido()));

    // Sumar precios de productos del pedido cargado
    double total = pedido.getProductos()
                         .stream()
                         .mapToDouble(producto -> producto.getPrecio() != null ? producto.getPrecio() : 0)
                         .sum();

    // Setear total en la venta
    venta.setTotal(total);

    // Asociar el pedido cargado a la venta para evitar sobreescritura con objeto "incompleto"
    venta.setPedido(pedido);

    // Guardar la venta
    Venta ventaGuardada = ventaRepository.save(venta);

    // Actualizar estado del pedido a "pagado"
    pedido.setEstado("pagado");
    pedidoRepository.save(pedido);

    return ventaGuardada;
}



    // Actualizar una venta existente
    public Venta actualizarVenta(Long id, Venta venta) {
    Venta ventaExistente = ventaRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Venta no encontrada con ID: " + id));

    ventaExistente.setCliente(venta.getCliente());
    ventaExistente.setPedido(venta.getPedido());
    return ventaRepository.save(ventaExistente);
    }

    // Eliminar una venta
    public void eliminarVenta(Long id) {
    if (!ventaRepository.existsById(id)) {
        throw new EntityNotFoundException("Venta no encontrada con ID: " + id);
    }
    ventaRepository.deleteById(id);
    }
}
