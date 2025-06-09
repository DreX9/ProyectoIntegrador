package com.example.integrador.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vista_historial_compras")
@Data
@NoArgsConstructor
public class HistoriaCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_compra")
    private Integer id;
    @Column(name = "fecha_compra")
    private String fechaCompra;
    @Column(name = "nombre_proveedor")
    private String nombreProveedor;
    @Column(name = "nombre_producto")
    private String nombreProducto;
    @Column(name = "nombre_almacen")
    private String nombreAlmacen;
    private Integer cantidad;
    @Column(name = "precio_unitario")
    private Double precio;
    @Column(name = "peso_total_kg")
    private Double pesoTotalKg;
    private Double subtotal;
    @Column(name = "nombre_usuario")
    private String nombreUsuario;
}
