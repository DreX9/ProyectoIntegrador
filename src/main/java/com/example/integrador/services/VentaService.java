package com.example.integrador.services;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.integrador.entities.Clasificacion;
import com.example.integrador.entities.DetalleCompra;
import com.example.integrador.entities.DetalleVenta;
import com.example.integrador.entities.Inventario;
import com.example.integrador.entities.Producto;
import com.example.integrador.entities.Venta;
import com.example.integrador.repositories.DetalleVentaRepository;
import com.example.integrador.repositories.InventarioRepository;
import com.example.integrador.repositories.VentaRepository;

import jakarta.transaction.Transactional;

@Service
public class VentaService {
    private final VentaRepository ventaRepository;
    private final DetalleVentaRepository detalleVentaRepository;
    private final InventarioRepository inventarioRepository;

    public VentaService(VentaRepository ventaRepository, DetalleVentaRepository detalleVentaRepository,
            InventarioRepository inventarioRepository) {
        this.ventaRepository = ventaRepository;
        this.detalleVentaRepository = detalleVentaRepository;
        this.inventarioRepository = inventarioRepository;
    }

    @Transactional
    public void registrarVenta(Venta venta) {
        List<DetalleVenta> detalles = venta.getDetalles();
        if (detalles == null || detalles.isEmpty()) {
            throw new RuntimeException("Debe haber al menos un detalle de venta.");
        }

        for (DetalleVenta detalle : detalles) {
            validarDetalle(detalle);
            double subtotal = detalle.getPeso() * detalle.getPrecio();
            detalle.setSubTotal(subtotal);
            detalle.setVenta(venta);

            double pesoNecesario = detalle.getPeso();
            List<Inventario> inventarios = inventarioRepository
                    .findByDetalleCompra_Producto_Id(detalle.getInventario().getDetalleCompra().getProducto().getId());
            // Ordenar inventarios por fecha de ingreso (suponiendo que hay un campo así)
            inventarios.sort(Comparator.comparing(Inventario::getFechaActualizacion));

            Iterator<Inventario> iterator = inventarios.iterator();
            while (iterator.hasNext() && pesoNecesario > 0) {
                Inventario inv = iterator.next();
                double disponible = inv.getPesoDisponible();
                if (disponible <= 0)
                    continue;

                double usado = Math.min(disponible, pesoNecesario);
                inv.setPesoDisponible(disponible - usado);
                pesoNecesario -= usado;

                // 🧠 Aquí el cálculo automático de javas/sacos/mariscos
                DetalleCompra detalleCompra = inv.getDetalleCompra();
                Producto producto = detalleCompra.getProducto();
                Clasificacion clasificacion = producto.getClasificacion();

                Double pesoUnidad = clasificacion.getPeso(); // Ej. 20kg por java
                if (pesoUnidad != null && pesoUnidad > 0) {
                    // ⚠️ Cuidado: solo se descuentan unidades completas
                    int cantidadActual = inv.getCantidadDisponible() != null ? inv.getCantidadDisponible() : 0;

                    

                    // 🔽 ¿Cuánto se está usando ahora?
                    int unidadesUsadas = (int) Math.floor(usado / pesoUnidad);

                    // En caso el último "trozo" sea parcial (ej. 3kg), también se descuenta 1
                    // unidad
                    if (usado % pesoUnidad > 0 && cantidadActual > 0) {
                        unidadesUsadas += 1;
                    }

                    inv.setCantidadDisponible(
                            Math.max(0, cantidadActual - unitsBounded(unidadesUsadas, cantidadActual)));
                }

                inventarioRepository.save(inv);
            }

            if (pesoNecesario > 0) {
                throw new RuntimeException("No hay suficiente stock para completar la venta.");
            }
        }

        double totalSinDescuento = detalles.stream().mapToDouble(DetalleVenta::getSubTotal).sum();
        double descuento = venta.getDescuento() != null ? venta.getDescuento() : 0;
        double montoConDescuento = totalSinDescuento * (1 - descuento);
        double igv = montoConDescuento * 0.18;

        venta.setIgv(igv);
        venta.setTotal(montoConDescuento + igv);

        ventaRepository.save(venta);
        detalleVentaRepository.saveAll(detalles);
    }

    private int unitsBounded(int requested, int available) {
        return Math.min(requested, available);
    }

    private void validarDetalle(DetalleVenta detalle) {
        if (detalle.getPeso() == null || detalle.getPeso() <= 0)
            throw new RuntimeException("Peso inválido para el producto seleccionado.");

        if (detalle.getPrecio() == null || detalle.getPrecio() <= 0)
            throw new RuntimeException("Precio inválido para el producto seleccionado.");

        Inventario inventario = inventarioRepository.findById(detalle.getInventario().getId())
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));

        detalle.setInventario(inventario);
        if (inventario.getDetalleCompra() == null)
            throw new RuntimeException("El inventario no tiene asociado un detalle de compra.");

        Producto producto = inventario.getDetalleCompra().getProducto();
        if (producto == null)
            throw new RuntimeException("El detalle de compra no tiene un producto válido.");

        Clasificacion clasificacion = producto.getClasificacion();
        if (clasificacion == null)
            throw new RuntimeException("El producto no tiene una clasificación definida.");

        Double pesoMaximo = clasificacion.getPeso();
        if (pesoMaximo == null)
            throw new RuntimeException("La clasificación no tiene definido un peso máximo.");
    }
}
