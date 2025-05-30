package com.FullStack.Prueba2.model.envio;

import com.FullStack.Prueba2.model.cliente.Cliente;
import com.FullStack.Prueba2.model.venta.Venta;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Envio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEnvio;
    @OneToOne
    @JoinColumn(name = "venta_id_venta")
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "cliente_id_cliente")
    @JsonBackReference
    private Cliente cliente;
    private String direccionEnvio, fechaEnvio, estadoEnvio;

}
