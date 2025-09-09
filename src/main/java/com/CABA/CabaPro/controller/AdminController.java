package com.CABA.CabaPro.controller;

import com.CABA.CabaPro.model.Asignacion;
import com.CABA.CabaPro.model.Partido;
import com.CABA.CabaPro.model.Torneo;
import com.CABA.CabaPro.service.AsignacionService;
import com.CABA.CabaPro.service.PartidoService;
import com.CABA.CabaPro.service.TorneoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private TorneoService torneoService;

    @Autowired
    private PartidoService partidoService;

    @Autowired
    private AsignacionService asignacionService;

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        List<Torneo> torneos = torneoService.getAllTorneos();
        List<Partido> partidos = partidoService.getAllPartidos();
        List<Asignacion> asignaciones = asignacionService.getAllAsignaciones();

        model.addAttribute("torneos", torneos);
        model.addAttribute("partidos", partidos);
        model.addAttribute("asignaciones", asignaciones);
        model.addAttribute("totalTorneos", torneos.size());
        model.addAttribute("totalPartidos", partidos.size());
        model.addAttribute("totalAsignaciones", asignaciones.size());

        return "admin/dashboard";
    }
}