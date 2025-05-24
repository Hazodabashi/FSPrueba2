package com.FullStack.Prueba2.service.cliente;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FullStack.Prueba2.model.cliente.Cliente;
import com.FullStack.Prueba2.model.cliente.Resena;
import com.FullStack.Prueba2.model.gestionInventario.Producto;
import com.FullStack.Prueba2.repository.cliente.ClienteRepository;
import com.FullStack.Prueba2.repository.cliente.ResenaRepository;
import com.FullStack.Prueba2.repository.gestioninventario.ProductoRepository;

@Service
public class ResenaService {
    @Autowired
    ResenaRepository resenaRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ProductoRepository productoRepository;

    //Listar
public String getAllResenas() {
    StringBuilder output = new StringBuilder();

    for (Resena resena : resenaRepository.findAll()) {
        output.append("ID Reseña: ").append(resena.getIdResena()).append("\n");
        output.append("Calificación: ").append(resena.getCalificacion()).append("\n");

        if (resena.getCliente() != null) {
            output.append("Cliente ID: ").append(resena.getCliente().getIdCliente()).append("\n");
            output.append("Cliente Nombre: ").append(resena.getCliente().getNombreCliente()).append("\n");
        } else {
            output.append("Cliente: Sin asignar\n");
        }

        if (resena.getProducto() != null) {
            output.append("Producto ID: ").append(resena.getProducto().getIdProducto()).append("\n");
            output.append("Producto Nombre: ").append(resena.getProducto().getNombre()).append("\n");
        } else {
            output.append("Producto: Sin asignar\n");
        }

        output.append("Comentario: ").append(resena.getComentario()).append("\n");
        output.append("------------------o------------------\n\n");
    }

    if (output.isEmpty()) {
        return "No se encontraron reseñas";
    } else {
        return output.toString();
    }
}
    //Buscar
public String getResenaById(Long id) {
    if (resenaRepository.existsById(id)) {
        Resena buscado = resenaRepository.findById(id).get();
        StringBuilder output = new StringBuilder();
        output.append("ID Reseña: ").append(buscado.getIdResena()).append("\n");
        output.append("Calificación: ").append(buscado.getCalificacion()).append("\n");

        if (buscado.getCliente() != null) {
            output.append("Cliente ID: ").append(buscado.getCliente().getIdCliente()).append("\n");
            output.append("Cliente Nombre: ").append(buscado.getCliente().getNombreCliente()).append("\n");
        } else {
            output.append("Cliente: Sin asignar\n");
        }

        if (buscado.getProducto() != null) {
            output.append("Producto ID: ").append(buscado.getProducto().getIdProducto()).append("\n");
            output.append("Producto Nombre: ").append(buscado.getProducto().getNombre()).append("\n");
        } else {
            output.append("Producto: Sin asignar\n");
        }

        output.append("Comentario: ").append(buscado.getComentario()).append("\n");
        return output.toString();
    } else {
        return "No se encontraron reseñas con esa ID";
    }
}

    //Agregar
public String addResena(Resena resena) {
    resenaRepository.save(resena);
    return "Reseña agregada correctamente";
}

    //Eliminar
    public String deleteResena(Long id){
        if(resenaRepository.existsById(id)){
            resenaRepository.deleteById(id);
            return "Reseña eliminada correctamente";
        }else{
            return "No se encontraron reseñas con esa ID";
        }
    }
    //Actualizar
    public String updateResena(Long id, Resena resena){
        if(resenaRepository.existsById(id)){
            Resena buscado = resenaRepository.findById(id).get();
            buscado.setCalificacion(resena.getCalificacion());
            buscado.setCliente(resena.getCliente());
            buscado.setProducto(resena.getProducto());
            buscado.setComentario(resena.getComentario());
            resenaRepository.save(buscado);
            return "Reseña actualizada correctamente";
        }else{
            return "No se encontraron reseñas con esa ID";
        }
    }

public String addResenaACliente(Map<String, Object> datos) {
    try {
        Long idCliente = Long.valueOf(datos.get("idCliente").toString());
        Long idProducto = Long.valueOf(datos.get("idProducto").toString());
        Integer calificacion = Integer.valueOf(datos.get("calificacion").toString());
        String comentario = datos.get("comentario").toString();

        Optional<Cliente> clienteOpt = clienteRepository.findById(idCliente);
        Optional<Producto> productoOpt = productoRepository.findById(idProducto);

        if (clienteOpt.isPresent() && productoOpt.isPresent()) {
            Resena resena = new Resena();
            resena.setCliente(clienteOpt.get());
            resena.setProducto(productoOpt.get());
            resena.setCalificacion(calificacion);
            resena.setComentario(comentario);

            resenaRepository.save(resena);

            // Opcional: agregar a la lista del cliente
            Cliente cliente = clienteOpt.get();
            cliente.getResenas().add(resena);
            clienteRepository.save(cliente);

            return "Reseña agregada correctamente al cliente y producto";
        } else {
            return "Cliente o producto no encontrado";
        }
    } catch (Exception e) {
        return "Error al procesar los datos: " + e.getMessage();
    }
}
}