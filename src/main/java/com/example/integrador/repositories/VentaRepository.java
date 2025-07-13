package com.example.integrador.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.integrador.entities.Venta;

public interface VentaRepository extends JpaRepository<Venta, Integer> {
    List<Venta> findByFechaVentaAfter(LocalDate fecha);

    @Query("SELECT COUNT(v) FROM Venta v")
    int contarVentas();

    @Query("SELECT SUM(d.subTotal) FROM DetalleVenta d")
    Double obtenerGananciaTotal();

    @Query("SELECT d.inventario.detalleCompra.producto.nombre " +
            "FROM DetalleVenta d " +
            "GROUP BY d.inventario.detalleCompra.producto.nombre " +
            "ORDER BY SUM(d.peso) DESC")
    List<String> obtenerProductosOrdenadosPorVenta();
}
