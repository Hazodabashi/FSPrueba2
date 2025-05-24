package com.FullStack.Prueba2.service.venta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FullStack.Prueba2.model.venta.Venta;
import com.FullStack.Prueba2.repository.venta.VentaRepository;

@Service
public class VentaService {
    @Autowired
    private VentaRepository ventaRepository;

    //Listar
    public String getAllVentas() {
        String output = "";

        for (Venta venta : ventaRepository.findAll()) {
            output += "ID Venta: " + venta.getIdVenta() + "\n";
            output += "Usuario: " + venta.getCliente() + "\n";
            output += "Productos Asignados: " + venta.getProductosVenta() + "\n";

        }
        if(output.isEmpty()){
            return "No se encontraron ventas";
        }else{
            return output;
        }
    }

    //Buscar
    public String getVentaById(Long id) {
        String output = "";
        if (ventaRepository.existsById(id)) {
            Venta buscado = ventaRepository.findById(id).get();
            output += "ID Venta: " + buscado.getIdVenta() + "\n";
            output += "Usuario: " + buscado.getCliente() + "\n";
            output += "Productos Asignados: " + buscado.getProductosVenta() + "\n";
            return output;
        }else{
            return "No se encontraron ventas con esa ID";
        }
    }
    //Agregar
public String addVenta(Venta venta) {
    ventaRepository.save(venta);
    return "Venta agregada correctamente";
}

    //Eliminar
    public String deleteVenta(Long id) {
        if(ventaRepository.existsById(id)){
            ventaRepository.deleteById(id);
            return "Venta eliminada correctamente";
        }else{
            return "No se encontraron ventas con esa ID";
        }
    }
    //Actualizar
    public String updateVenta(Long id, Venta venta) {
        if(ventaRepository.existsById(id)){
            Venta buscado = ventaRepository.findById(id).get();
            buscado.setCliente(venta.getCliente());
            buscado.setProductosVenta(venta.getProductosVenta());
            ventaRepository.save(buscado);
            return "Venta actualizada correctamente";
        }else{
            return "No se encontraron ventas con esa ID";
        }
    }
}
