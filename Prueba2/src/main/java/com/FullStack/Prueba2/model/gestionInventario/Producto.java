package com.FullStack.Prueba2.model.gestionInventario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;
    private Integer stock;
    @ManyToOne
    @JoinColumn(name = "proveedor_id", referencedColumnName = "id")
    private Proveedor proveedor;
    private String nombre, descripcion, categoria;
    private Double precio;
}