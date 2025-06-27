package com.example.integrador.services;

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

    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    public Venta obtenerVentaPorId(Integer id) {
        return ventaRepository.findById(id).orElse(new Venta());
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

            // 💡 Descontar del inventario el peso vendido
            Inventario inventario = detalle.getInventario();
            double pesoActual = inventario.getPesoDisponible();
            double pesoVendido = detalle.getPeso();

            if (pesoVendido > pesoActual) {
                throw new RuntimeException(
                        "No hay suficiente stock en el inventario. Disponible: " + pesoActual + " kg");
            }

            inventario.setPesoDisponible(pesoActual - pesoVendido);
            inventarioRepository.save(inventario);
            // Si también manejas cantidad por unidad:
            // inventario.setCantidadDisponible(inventario.getCantidadDisponible() - 1);
        }

        double totalSinDescuento = detalles.stream()
                .mapToDouble(DetalleVenta::getSubTotal)
                .sum();

        double descuento = venta.getDescuento() != null ? venta.getDescuento() : 0;
        double montoConDescuento = totalSinDescuento * (1 - descuento);

        double igv = montoConDescuento * 0.18;
        venta.setIgv(igv);
        venta.setTotal(montoConDescuento + igv);

        ventaRepository.save(venta);
        detalleVentaRepository.saveAll(detalles);
        



        System.out.println("Subtotal: " + totalSinDescuento);
System.out.println("Descuento recibido: " + descuento);
System.out.println("Monto con descuento: " + montoConDescuento);
System.out.println("IGV: " + igv);
System.out.println("TOTAL FINAL: " + (montoConDescuento + igv));
    }

    private void validarDetalle(DetalleVenta detalle) {
        if (detalle.getPeso() == null || detalle.getPeso() <= 0) {
            throw new RuntimeException("Peso inválido para el producto seleccionado.");
        }

        if (detalle.getPrecio() == null || detalle.getPrecio() <= 0) {
            throw new RuntimeException("Precio inválido para el producto seleccionado.");
        }

        Inventario inventario = inventarioRepository.findById(detalle.getInventario().getId())
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));

        detalle.setInventario(inventario);
        if (inventario == null) {
            throw new RuntimeException("Debe seleccionar un inventario válido.");
        }

        DetalleCompra detalleCompra = inventario.getDetalleCompra();
        if (detalleCompra == null) {
            throw new RuntimeException("El inventario no tiene asociado un detalle de compra.");
        }

        Producto producto = detalleCompra.getProducto();
        if (producto == null) {
            throw new RuntimeException("El detalle de compra no tiene un producto válido.");
        }

        Clasificacion clasificacion = producto.getClasificacion();
        if (clasificacion == null) {
            throw new RuntimeException("El producto no tiene una clasificación definida.");
        }

        Double pesoMaximo = clasificacion.getPeso(); // límite por presentación
        if (pesoMaximo == null) {
            throw new RuntimeException("La clasificación no tiene definido un peso máximo.");
        }

        if (detalle.getPeso() > pesoMaximo) {
            throw new RuntimeException("No puedes vender más de " + pesoMaximo + " kg para esta presentación.");
        }
    }
}
