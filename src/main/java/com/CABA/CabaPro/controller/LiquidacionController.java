package com.CABA.CabaPro.controller;

import com.CABA.CabaPro.model.Liquidacion;
import com.CABA.CabaPro.model.Usuario;
import com.CABA.CabaPro.model.Asignacion;
import com.CABA.CabaPro.model.EstadoAsignacionEnum;
import com.CABA.CabaPro.service.LiquidacionService;
import com.CABA.CabaPro.service.UsuarioService;
import com.CABA.CabaPro.service.AsignacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.time.LocalDate;
import java.time.YearMonth;
import java.math.BigDecimal;

@Controller
public class LiquidacionController {

    @Autowired
    private LiquidacionService liquidacionService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private AsignacionService asignacionService;

    // Muestra el formulario para generar una liquidación
    @GetMapping("/arbitro/liquidaciones")
    public String mostrarFormularioLiquidacion(HttpSession session, Model model) {
        Usuario arbitro = (Usuario) session.getAttribute("usuario");
        if (arbitro == null) {
            return "redirect:/login";
        }
        model.addAttribute("arbitro", arbitro);
        return "arbitro/liquidaciones";
    }

    // Procesa el formulario y genera la liquidación
    @PostMapping("/arbitro/liquidaciones/generar")
    public String generarLiquidacion(
            @RequestParam int mes,
            @RequestParam int anio,
            HttpSession session,
            Model model) {
        Usuario arbitro = (Usuario) session.getAttribute("usuario");
        if (arbitro == null) {
            return "redirect:/login";
        }

        // Obtener asignaciones aceptadas del árbitro para el mes/año especificado
        List<Asignacion> asignacionesAceptadas = asignacionService.findByUsuarioAndEstadoAndMesAnio(
                arbitro, EstadoAsignacionEnum.ACEPTADA, mes, anio);

        // Calcular monto total basado en las tarifas de las asignaciones
        java.math.BigDecimal montoTotal = java.math.BigDecimal.ZERO;
        for (Asignacion asignacion : asignacionesAceptadas) {
            if (asignacion.getPartido() != null && asignacion.getPartido().getTorneo() != null) {
                java.math.BigDecimal tarifa = asignacion.getPartido().getTorneo()
                        .getTarifaPorEscalafon(arbitro.getEscalafon());
                if (tarifa != null) {
                    montoTotal = montoTotal.add(tarifa);
                }
            }
        }

        // Crear nueva liquidación
        Liquidacion liquidacion = new Liquidacion();
        liquidacion.setUsuario(arbitro);
        liquidacion.setMes(mes);
        liquidacion.setAnio(anio);
        liquidacion.setMontoTotal(montoTotal);
        liquidacion.setPartidas(asignacionesAceptadas);

        liquidacionService.saveLiquidacion(liquidacion);

        // Redirigir a la vista de liquidación generada
        return "redirect:/arbitro/liquidaciones/generada?id=" + liquidacion.getId();
    }

    @GetMapping("/arbitro/liquidaciones/generada")
    public String verLiquidacionGenerada(@RequestParam Long id, HttpSession session, Model model) {
        Usuario arbitro = (Usuario) session.getAttribute("usuario");
        if (arbitro == null) {
            return "redirect:/login";
        }
        Liquidacion liquidacion = liquidacionService.getLiquidacionById(id).orElse(null);
        if (liquidacion == null || !liquidacion.getUsuario().getCorreo().equals(arbitro.getCorreo())) {
            return "redirect:/arbitro/liquidaciones";
        }
        model.addAttribute("liquidacion", liquidacion);
        model.addAttribute("asignaciones", liquidacion.getPartidas());
        return "arbitro/liquidacion-generada";
    }

    @GetMapping("/arbitro/liquidaciones/{id}/pdf")
    public ResponseEntity<byte[]> generarPDF(@org.springframework.web.bind.annotation.PathVariable Long id,
            HttpSession session) {
        Usuario arbitro = (Usuario) session.getAttribute("usuario");
        if (arbitro == null) {
            return ResponseEntity.status(401).build();
        }

        Liquidacion liquidacion = liquidacionService.getLiquidacionById(id).orElse(null);
        // Validar por correo (más robusto que por id si no hay id en usuario)
        if (liquidacion == null || liquidacion.getUsuario() == null
                || !liquidacion.getUsuario().getCorreo().equals(arbitro.getCorreo())) {
            return ResponseEntity.notFound().build();
        }

        // Generar PDF (implementación básica)
        byte[] pdfContent = liquidacionService.generarPDF(liquidacion);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment",
                "liquidacion_" + liquidacion.getMes() + "_" + liquidacion.getAnio() + ".pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfContent);
    }
}
