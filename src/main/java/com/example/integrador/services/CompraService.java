package com.example.integrador.services;

import java.util.List;
import org.springframework.stereotype.Service;

import com.example.integrador.entities.Compra;
import com.example.integrador.entities.DetalleCompra;
import com.example.integrador.repositories.CompraRepository;
import com.example.integrador.repositories.DetalleCompraRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompraService {

    private final CompraRepository compraRepository;
    private final DetalleCompraRepository detalleCompraRepository;

    public List<Compra> listarCompras() {
        return compraRepository.findAll();
    }

    public Compra obtenerCompraPorId(Integer id) {
        return compraRepository.findById(id).orElse(new Compra());
    }

    @Transactional
    public void registrarCompra(Compra compra) {
        List<DetalleCompra> detalles = compra.getDetalles();

        if (detalles == null || detalles.isEmpty()) {
            throw new RuntimeException("Debe haber al menos un detalle de compra.");
        }

        // Calcular subtotales para cada detalle y luego el total general
        for (DetalleCompra detalle : detalles) {
            validarDetalle(detalle);

            double subtotal = detalle.getPesoTotal() * detalle.getPrecioUnitario();
            detalle.setSubtotal(subtotal);

            // Relacionar el detalle con la compra
            detalle.setCompra(compra);
        }

        double montoTotal = detalles.stream()
                .mapToDouble(DetalleCompra::getSubtotal)
                .sum();

        compra.setTotal(montoTotal);
        compraRepository.save(compra);

        // Guardar todos los detalles
        detalleCompraRepository.saveAll(detalles);
    }

    private void validarDetalle(DetalleCompra detalle) {
        if (detalle.getCantidad() == null || detalle.getCantidad() <= 0) {
            throw new RuntimeException("Cantidad inválida para el producto: " + detalle.getProducto().getNombre());
        }

        if (detalle.getPrecioUnitario() == null || detalle.getPrecioUnitario() <= 0) {
            throw new RuntimeException(
                    "Precio unitario inválido para el producto: " + detalle.getProducto().getNombre());
        }

        if (detalle.getPesoTotal() == null || detalle.getPesoTotal() <= 0) {
            throw new RuntimeException("Peso total inválido para el producto: " + detalle.getProducto().getNombre());
        }

        if (detalle.getProducto() != null && detalle.getProducto().getClasificacion() != null) {
            Double pesoUnidad = detalle.getProducto().getClasificacion().getPeso(); // Por ejemplo, 20kg por unidad
            if (pesoUnidad != null && pesoUnidad > 0) {
                double pesoEsperado = detalle.getCantidad() * pesoUnidad;
                if (Math.abs(detalle.getPesoTotal() - pesoEsperado) > 0.1) { // tolerancia de 0.1 kg
                    throw new RuntimeException(
                            "El peso total no concuerda con la cantidad según la presentación. Esperado: "
                                    + pesoEsperado + " kg para " + detalle.getCantidad() + " unidades.");
                }
            }
        }
    }

    public int contarCompras() {
        return compraRepository.contarCompras();
    }
}
