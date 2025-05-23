package com.FullStack.Prueba2.model.cliente;

import java.util.ArrayList;
import java.util.List;

import com.FullStack.Prueba2.model.gestionInventario.Producto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;
    @ManyToOne
    @JoinColumn(name = "cliente_id_cliente", referencedColumnName = "idCliente")
    private Cliente cliente;
    private String estado;
    @ManyToMany
    @JoinTable(name = "pedido_producto", joinColumns = @JoinColumn(name = "pedido_id_pedido"), inverseJoinColumns = @JoinColumn(name = "producto_id_producto"))
    private List<Producto> productos = new ArrayList<>();


}
