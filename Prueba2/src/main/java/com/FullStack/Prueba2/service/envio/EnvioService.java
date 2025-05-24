package com.FullStack.Prueba2.service.envio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FullStack.Prueba2.model.envio.Envio;
import com.FullStack.Prueba2.repository.envio.EnvioRepository;

@Service
public class EnvioService {
    @Autowired
    private EnvioRepository envioRepository;

    //Listar
    public String getAllEnvios() {
        String output = "";

        for (Envio envio : envioRepository.findAll()) {
            output +="ID Envio: "+envio.getIdEnvio()+"\n";
            output +="Pedido: "+envio.getPedido()+"\n";
            output +="Cliente: "+envio.getCliente()+"\n";
            output +="Direccion Envio: "+envio.getDireccionEnvio()+"\n";
            output +="Fecha Envio: "+envio.getFechaEnvio()+"\n";
            output +="Estado Envio: "+envio.getEstadoEnvio()+"\n";
        }
        if(output.isEmpty()){
            return "No se encontraron envios";
        }else{
            return output;
        }
    }

    //Buscar
    public String getEnvioById(Long id){
        String output = "";
        if(envioRepository.existsById(id)){
            Envio buscado = envioRepository.findById(id).get();
            output += "ID Envio: "+buscado.getIdEnvio()+"\n";
            output += "Pedido: "+buscado.getPedido()+"\n";
            output += "Cliente: "+buscado.getCliente()+"\n";
            output += "Direccion Envio: "+buscado.getDireccionEnvio()+"\n";
            output += "Fecha Envio: "+buscado.getFechaEnvio();
            output += "Estado Envio: "+buscado.getEstadoEnvio()+"\n";
            return output;
        }else{
            return "No se encontraron envios con esa ID";
        }
    }
    //Agregar
public String addEnvio(Envio envio) {
    envioRepository.save(envio);
    return "Envío agregado correctamente";
}

    //Eliminar
    public String deleteEnvio(Long id){
        if(envioRepository.existsById(id)){
            envioRepository.deleteById(id);
            return "Envio eliminado correctamente";
        }else{
            return "No se encontraron envios con esa ID";
        }
    }
    //Actualizar
    public String updateEnvio(Long id, Envio envio){
        if(envioRepository.existsById(id)){
            Envio buscado = envioRepository.findById(id).get();
            buscado.setPedido(envio.getPedido());
            buscado.setCliente(envio.getCliente());
            buscado.setDireccionEnvio(envio.getDireccionEnvio());
            buscado.setFechaEnvio(envio.getFechaEnvio());
            buscado.setEstadoEnvio(envio.getEstadoEnvio());
            envioRepository.save(buscado);
            return "Envio actualizado correctamente";
        }else{
            return "No se encontraron envios con esa ID";
        }
    }
}
