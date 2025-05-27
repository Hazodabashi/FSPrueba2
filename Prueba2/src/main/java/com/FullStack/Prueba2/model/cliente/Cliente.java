package com.FullStack.Prueba2.model.cliente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    @Column(nullable = false)
    private String nombreCliente, emailCliente, direccionCliente;
    
    @Column(length = 13,nullable = false)
    private String run;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "cliente_resena", 
    joinColumns = @JoinColumn(name = "cliente_id_cliente"), 
    inverseJoinColumns = @JoinColumn(name = "resena_id_resena"))
    private List<Resena> resenas = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "cliente_pedido", 
    joinColumns = @JoinColumn(name = "cliente_id_cliente"), 
    inverseJoinColumns = @JoinColumn(name = "pedido_id_pedido"))
    @JsonManagedReference
    private List<Pedido> pedidos = new ArrayList<>();




}
