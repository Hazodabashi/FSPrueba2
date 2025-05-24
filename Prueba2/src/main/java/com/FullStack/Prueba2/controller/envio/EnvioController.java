package com.FullStack.Prueba2.controller.envio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.FullStack.Prueba2.model.envio.Envio;
import com.FullStack.Prueba2.service.envio.EnvioService;

@RestController
@RequestMapping("/api/envios")

public class EnvioController {

    @Autowired
    private EnvioService envioService;

    @GetMapping
    public String getAllEnvios() {return envioService.getAllEnvios(); }

    @PostMapping
    public String addEnvio(@RequestBody Envio envio) {return envioService.addEnvio(envio); }

    @GetMapping("/{id}")
    public String getEnvioById(@PathVariable Long id) {return envioService.getEnvioById(id); }

    @DeleteMapping("/{id}")
    public String deleteEnvio(@PathVariable Long id) {return envioService.deleteEnvio(id); }

    @PutMapping("/{id}")
    public String updateEnvio(@PathVariable Long id, @RequestBody Envio envio) {
        return envioService.updateEnvio(id,envio);
    }
}