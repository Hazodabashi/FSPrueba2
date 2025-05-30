package com.FullStack.Prueba2.service.cliente;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.FullStack.Prueba2.model.cliente.Resena;
import com.FullStack.Prueba2.repository.cliente.ResenaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ResenaService {
    @Autowired
    ResenaRepository resenaRepository;

    //Listar
    public List<Resena> getAllResenas() {
    return resenaRepository.findAll();
    }

    
    //Buscar
    public Resena getResenaById(Long id) {
    return resenaRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Resena no encontrado con ID: " + id));
    }

    //Agregar
    public Resena addResena(Resena resena) {
    return resenaRepository.save(resena);
    }

    //Eliminar
    public void eliminarResena(Long id) {
    if (!resenaRepository.existsById(id)) {
        throw new EntityNotFoundException("Reseña no encontrado con ID: " + id);
    }
    resenaRepository.deleteById(id);
    }


    //Actualizar
    public Resena actualizarResena(Long id, Resena resenaActualizada) {
        Resena existente = resenaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reseña no encontrada con ID: " + id));

        existente.setCalificacion(resenaActualizada.getCalificacion());
        existente.setComentario(resenaActualizada.getComentario());
        existente.setProducto(resenaActualizada.getProducto());
        existente.setCliente(resenaActualizada.getCliente());

        return resenaRepository.save(existente);
    }

}