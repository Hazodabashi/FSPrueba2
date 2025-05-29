package com.FullStack.Prueba2.model.venta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import com.FullStack.Prueba2.model.cliente.Cliente;
import com.FullStack.Prueba2.model.gestionInventario.Producto;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVenta;
    
    @ManyToOne
    @JoinColumn(name = "cliente_id_cliente")
    @JsonBackReference
    private Cliente cliente;

    @ManyToMany
    @JoinTable(name = "venta_producto", joinColumns = @JoinColumn(name = "venta_id_venta"), inverseJoinColumns = @JoinColumn(name = "producto_id_producto"))
    private List<Producto> productosVenta = new ArrayList<>();


}
