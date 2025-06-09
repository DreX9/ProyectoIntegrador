package com.example.integrador.services;

import java.util.List;

import org.springframework.stereotype.Service;
import com.example.integrador.entities.DetalleCompra;
import com.example.integrador.repositories.DetalleCompraRepository;

import lombok.RequiredArgsConstructor;

// @Service
// @RequiredArgsConstructor
// public class DetalleCompraService {

//     private final DetalleCompraRepository detalleRepository;
    
//     // Ver todas las compras (opcional: para historial)
//     public List<DetalleCompra> detalleSeDetalleCompras(){
//         return detalleRepository.findAll();
//     }

//     // Ver una compra específica
//     public DetalleCompra detalleSelectOne(Integer id) {
//         return detalleRepository.findById(id).orElse(null);
//     }
//     // Registrar una nueva compra
//     public DetalleCompra DetalleCompraInset(DetalleCompra detalle){
//         return detalleRepository.save(detalle);
//     }

// }
