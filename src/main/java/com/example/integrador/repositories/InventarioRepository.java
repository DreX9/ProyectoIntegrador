package com.example.integrador.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.integrador.DTO.InventarioResumenDTO;
import com.example.integrador.entities.Inventario;

public interface InventarioRepository extends JpaRepository<Inventario, Integer> {
   List<Inventario> findByDetalleCompra_Producto_Id(Integer idProducto);

   @Query("SELECT new com.example.integrador.DTO.InventarioResumenDTO(" +
         "MAX(i.id), p.id, p.nombre, SUM(i.pesoDisponible)) " +
         "FROM Inventario i " +
         "JOIN i.detalleCompra dc " +
         "JOIN dc.producto p " +
         "WHERE i.pesoDisponible > 0 " +
         "GROUP BY p.id, p.nombre")
   List<InventarioResumenDTO> obtenerResumenInventario();

}
