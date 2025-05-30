package com.FullStack.Prueba2.service.envio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.FullStack.Prueba2.model.envio.Envio;
import com.FullStack.Prueba2.repository.envio.EnvioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EnvioService {
    @Autowired
    private EnvioRepository envioRepository;

    //Listar
    public List<Envio> getAllEnvios(){
    return envioRepository.findAll();
    }


    //Buscar
    public Envio getEnvioById(Long id) {
    return envioRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Envio no encontrado con ID: " + id));
    }


    //Agregar
    public Envio addEnvio(Envio envio) {
    return envioRepository.save(envio);
    }

    //Eliminar
    public void eliminarEnvio(Long id) {
    if (!envioRepository.existsById(id)) {
        throw new EntityNotFoundException("Envio no encontrado con ID: " + id);
    }
    envioRepository.deleteById(id);
    }


    //Actualizar
    public Envio actualizarEnvio(Long id, Envio envio) {
        Envio envioExistente = envioRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Envio no encontrado con ID: " + id));

        envioExistente.setDireccionEnvio(envio.getDireccionEnvio());
        envioExistente.setFechaEnvio(envio.getFechaEnvio());
        envioExistente.setEstadoEnvio(envio.getEstadoEnvio());
        /*envioExistente.setVenta(envio.getVenta());       // opcional, si permites actualizar la venta
        envioExistente.setCliente(envio.getCliente());   // opcional, si permites actualizar el cliente*/
        return envioRepository.save(envioExistente);
    }
}
