package com.FullStack.Prueba2.controller.cliente;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.FullStack.Prueba2.model.cliente.Resena;
import com.FullStack.Prueba2.service.cliente.ResenaService;

@RestController
@RequestMapping("/api/resenas")

public class ResenaController {

    @Autowired
    private ResenaService resenaService;

    @GetMapping
    public String getAllResenas() {return resenaService.getAllResenas(); }

    @PostMapping
    public String addResena(@RequestBody Resena resena) {return resenaService.addResena(resena); }

    @GetMapping("/{id}")
    public String getResenaById(@PathVariable Long id) {return resenaService.getResenaById(id); }

    @DeleteMapping("/{id}")
    public String deleteResena(@PathVariable Long id) { return resenaService.deleteResena(id); }

    @PutMapping("/{id}")
    public String updateResena(@PathVariable Long id, @RequestBody Resena resena) {
        return resenaService.updateResena(id, resena);
    }

    @PostMapping("/agregar")
    public String agregarResenaDesdeJson(@RequestBody Map<String, Object> datos) {
        return resenaService.addResenaACliente(datos);
    }
}