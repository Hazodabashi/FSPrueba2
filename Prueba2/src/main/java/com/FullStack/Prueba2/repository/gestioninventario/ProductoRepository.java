package com.FullStack.Prueba2.repository.gestioninventario;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FullStack.Prueba2.model.gestionInventario.Producto;

public interface ProductoRepository extends JpaRepository<Producto,Long> {

}
