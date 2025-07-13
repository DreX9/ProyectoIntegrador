package com.example.integrador.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.integrador.DTO.EstadisticaDTO;
import com.example.integrador.DTO.VentaResumenDTO;
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

    // Resumen de venta
    public List<VentaResumenDTO> obtenerResumenVentas() {
        List<Venta> ventas = ventaRepository.findAll();

        return ventas.stream().map(v -> {
            VentaResumenDTO dto = new VentaResumenDTO();
            dto.setIdVenta(v.getId());
            dto.setDniCliente(v.getCliente().getDni());
            dto.setNombreCliente(v.getCliente().getApellido());
            dto.setFecha(v.getFechaVenta());
            dto.setHora(v.getMomento().toLocalTime());
            dto.setTotal(v.getTotal());
            dto.setNombreUsuario(v.getUsuario().getNombre() + " " + v.getUsuario().getApellido());
            return dto;
        }).collect(Collectors.toList());
    }

    // pdf
    public Optional<Venta> buscarPorId(Integer id) {
        return ventaRepository.findById(id);
    }

    public EstadisticaDTO obtenerEstadisticasPorPeriodo(String periodo) {
        LocalDate inicio;

        switch (periodo) {
            case "hoy":
                inicio = LocalDate.now();
                break;
            case "semanal":
                inicio = LocalDate.now().minusDays(7);
                break;
            case "mensual":
                inicio = LocalDate.now().minusMonths(1);
                break;
            case "anual":
                inicio = LocalDate.now().minusYears(1);
                break;
            default:
                inicio = LocalDate.now().minusDays(7); // Por defecto semanal
        }

        List<Venta> ventas = ventaRepository.findByFechaVentaAfter(inicio);

        Map<String, Integer> cantidadesMap = new HashMap<>();
        Map<String, Double> ingresosMap = new HashMap<>();

        String productoMasVendido = "";
        int maxCantidad = 0;
        double totalGeneral = 0;

        for (Venta venta : ventas) {
            for (DetalleVenta detalle : venta.getDetalles()) {
                String nombre = detalle.getInventario().getDetalleCompra().getProducto().getNombre();
                int cantidad = (int) Math.round(detalle.getPeso());
                double ingreso = detalle.getSubTotal();

                cantidadesMap.merge(nombre, cantidad, Integer::sum);
                ingresosMap.merge(nombre, ingreso, Double::sum);

                if (cantidadesMap.get(nombre) > maxCantidad) {
                    maxCantidad = cantidadesMap.get(nombre);
                    productoMasVendido = nombre;
                }

                totalGeneral += ingreso;
            }
        }

        EstadisticaDTO dto = new EstadisticaDTO();
        dto.setProductoMasVendido(productoMasVendido);
        dto.setGananciaTotal(totalGeneral);
        dto.setEtiquetas(new ArrayList<>(cantidadesMap.keySet()));
        dto.setCantidades(new ArrayList<>(cantidadesMap.values()));
        dto.setIngresos(dto.getEtiquetas().stream().map(ingresosMap::get).collect(Collectors.toList()));

        return dto;
    }

    // Cantidad total de ventas
    public int contarVentas() {
        return ventaRepository.contarVentas();
    }

    // Suma total de ingresos por ventas
    public double obtenerGananciaTotal() {
        Double total = ventaRepository.obtenerGananciaTotal();
        return total != null ? total : 0.0;
    }

    // Nombre del producto más vendido (por peso total vendido)
    public String obtenerProductoMasVendido() {
        List<String> lista = ventaRepository.obtenerProductosOrdenadosPorVenta();
        return lista.isEmpty() ? "Sin datos" : lista.get(0);
    }

}
