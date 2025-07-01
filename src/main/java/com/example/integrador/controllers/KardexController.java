package com.example.integrador.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import com.example.integrador.repositories.ProductoRepository;
import com.example.integrador.services.KardexService;

@Controller
@RequestMapping("/kardexs")
public class KardexController {

    @Autowired
    private KardexService kardexService;

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping
    public String mostrarFormulario(Model model) {
        model.addAttribute("productos", productoRepository.findAll());
        return "kardexs";
    }

    @PostMapping("/exportar")
    public ResponseEntity<byte[]> exportarExcel(@RequestParam("idProducto") Integer idProducto) {
        if (!productoRepository.existsById(idProducto)) {
            return ResponseEntity.badRequest().build(); // o redirecciona con error
        }
        byte[] archivo = kardexService.generarKardexExcel(idProducto);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=kardex_producto_" + idProducto + ".xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(archivo);
    }
}