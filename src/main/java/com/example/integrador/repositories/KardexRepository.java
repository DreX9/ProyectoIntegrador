package com.example.integrador.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.integrador.entities.DetalleCompra;
import com.example.integrador.entities.KardexDTO;

@Repository
public interface KardexRepository extends JpaRepository<DetalleCompra, Integer> {

    @Query(value = "SELECT * FROM (" +
    "SELECT 'Entrada' AS tipo, c.fecha_compra AS fecha, p.nombre AS nombreProducto, " +
    "dc.peso_total_kg AS peso, dc.precio_unitario AS precioUnitario, " +
    "(dc.peso_total_kg * dc.precio_unitario) AS costoTotal, " +
    "c.momento AS momento " +  
    "FROM detalle_compra dc " +
    "JOIN compras c ON dc.id_compra = c.id_compra " +
    "JOIN productos p ON dc.id_producto = p.id_producto " +
    "WHERE dc.id_producto = :idProducto " +

    "UNION ALL " +

    "SELECT 'Salida' AS tipo, v.fecha_venta AS fecha, p.nombre AS nombreProducto, " +
    "dv.peso_total_kg AS peso, dv.precio_unitario AS precioUnitario, " +
    "(dv.peso_total_kg * dv.precio_unitario) AS costoTotal, " +
    "v.momento AS momento " +  
    "FROM detalle_venta dv " +
    "JOIN ventas v ON dv.id_venta = v.id_venta " +
    "JOIN inventario i ON dv.id_inventario = i.id_inventario " +
    "JOIN detalle_compra dc ON i.id_detalle_compra = dc.id_detalle_compra " +
    "JOIN productos p ON dc.id_producto = p.id_producto " +
    "WHERE dc.id_producto = :idProducto " +
    ") AS movimientos " +
    "ORDER BY momento ASC", nativeQuery = true)
List<KardexDTO> obtenerKardexPorProducto(@Param("idProducto") Integer idProducto);
}