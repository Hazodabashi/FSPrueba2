package com.FullStack.Prueba2.model.venta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.FullStack.Prueba2.model.cliente.Cliente;
import com.FullStack.Prueba2.model.cliente.Pedido;
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

    @OneToOne
    @JoinColumn(name = "pedido_id") // FK en la tabla VENTA
    private Pedido pedido;
    private Double total;
}
