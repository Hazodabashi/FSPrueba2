package com.FullStack.Prueba2.model.cliente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCliente;
    private String nombreCliente, emailCliente, direccionCliente, passwordCliente;
    @OneToMany
    @JoinTable(name = "cliente_resena", joinColumns = @JoinColumn(name = "cliente_id_cliente"), inverseJoinColumns = @JoinColumn(name = "resena_id_resena"))
    private List<Resena> resenas = new ArrayList<>();
    @OneToMany
    @JoinTable(name = "cliente_pedido", joinColumns = @JoinColumn(name = "cliente_id_cliente"), inverseJoinColumns = @JoinColumn(name = "pedido_id_pedido"))
    private List<Pedido> pedidos = new ArrayList<>();



}
