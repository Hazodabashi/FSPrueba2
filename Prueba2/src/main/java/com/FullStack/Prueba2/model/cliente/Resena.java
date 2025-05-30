package com.FullStack.Prueba2.model.cliente;



import com.FullStack.Prueba2.model.gestionInventario.Producto;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Resena {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idResena;
    private Integer calificacion;
    @ManyToOne
    @JoinColumn(name = "cliente_id_cliente")
    @JsonBackReference
    private Cliente cliente;
    @OneToOne
    @JoinColumn(name = "producto_id_producto")
    private Producto producto;
    private String comentario;
}



