package com.FullStack.Prueba2.model.envio;

import com.FullStack.Prueba2.model.cliente.Cliente;
import com.FullStack.Prueba2.model.cliente.Pedido;

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
    @ManyToOne
    @JoinColumn(name = "pedido_id_pedido")
    private Pedido pedido;
    @ManyToOne
    @JoinColumn(name = "cliente_id_cliente")
    private Cliente cliente;
    private String direccionEnvio, fechaEnvio, estadoEnvio;



}
