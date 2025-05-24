package com.FullStack.Prueba2.service.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FullStack.Prueba2.model.cliente.Resena;
import com.FullStack.Prueba2.repository.cliente.ResenaRepository;

@Service
public class ResenaService {
    @Autowired
    ResenaRepository resenaRepository;

    //Listar
    public String getAllResenas(){
        String output = "";

        for (Resena resena : resenaRepository.findAll()) {
            output += "ID Reseña: "+resena.getIdResena()+"\n";
            output += "Calificacion: "+resena.getCalificacion()+"\n";
            output += "Cliente: "+resena.getCliente()+"\n";
            output += "Producto: "+resena.getProducto()+"\n";
            output += "Comentario: "+resena.getComentario()+"\n";
        }
        if(output.isEmpty()){
            return "No se encontraron reseñas";
        }else{
            return output;
        }
    }
    //Buscar
    public String getResenaById(Long id){
        String output = "";
        if(resenaRepository.existsById(id)){
            Resena buscado = resenaRepository.findById(id).get();
            output += "ID Reseña "+buscado.getIdResena()+"\n";
            output += "Calificacion: "+buscado.getCalificacion()+"\n";
            output += "Cliente: "+buscado.getCliente()+"\n";
            output += "Producto: "+buscado.getProducto()+"\n";
            output += "Comentario: "+buscado.getComentario()+"\n";
            return output;
        }else{
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
}
