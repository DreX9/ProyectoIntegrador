package com.example.integrador.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.integrador.entities.Inventario;

public interface InventarioRepository extends JpaRepository<Inventario, Integer>{
   List<Inventario> findByDetalleCompra_Producto_Id(Integer idProducto);


}
