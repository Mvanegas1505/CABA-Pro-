package com.CABA.CabaPro.controller;

import com.CABA.CabaPro.model.Liquidacion;
import com.CABA.CabaPro.model.Usuario;
import com.CABA.CabaPro.service.LiquidacionService;
import com.CABA.CabaPro.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class LiquidacionController {
    @GetMapping("/arbitro/liquidaciones/generar")
    public String generarLiquidacion(HttpSession session, Model model) {
        Usuario arbitro = (Usuario) session.getAttribute("usuario");
        if (arbitro == null) {
            return "redirect:/login";
        }
        java.time.LocalDate fin = java.time.LocalDate.now();
        java.math.BigDecimal monto = java.math.BigDecimal.valueOf(1000); // ejemplo
        com.CABA.CabaPro.model.Liquidacion liquidacion = new com.CABA.CabaPro.model.Liquidacion();
        liquidacion.setUsuario(arbitro);
        liquidacion.setMes(fin.getMonthValue());
        liquidacion.setAnio(fin.getYear());
        liquidacion.setMontoTotal(monto);
        liquidacionService.saveLiquidacion(liquidacion);
        return "redirect:/arbitro/liquidaciones";
    }
    @Autowired
    private LiquidacionService liquidacionService;
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/arbitro/liquidaciones")
    public String verLiquidaciones(HttpSession session, Model model) {
        Usuario arbitro = (Usuario) session.getAttribute("usuario");
        if (arbitro == null) {
            return "redirect:/login";
        }
        List<Liquidacion> liquidaciones = liquidacionService.getLiquidacionesPorArbitro(arbitro.getCorreo());
        model.addAttribute("arbitro", arbitro);
        model.addAttribute("liquidaciones", liquidaciones);
        return "arbitro/liquidaciones";
    }
}
