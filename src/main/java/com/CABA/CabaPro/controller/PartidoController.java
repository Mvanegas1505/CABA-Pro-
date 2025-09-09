package com.CABA.CabaPro.controller;

import com.CABA.CabaPro.model.Partido;
import com.CABA.CabaPro.service.PartidoService;
import com.CABA.CabaPro.service.TorneoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PartidoController {

    @Autowired
    private PartidoService partidoService;

    @Autowired
    private TorneoService torneoService;

    @GetMapping("/admin/crear-partido")
    public String mostrarFormularioCrearPartido(Model model) {
        model.addAttribute("partido", new Partido());
        model.addAttribute("torneos", torneoService.getAllTorneos());
        return "admin/crear-partido";
    }

    @PostMapping("/admin/crear-partido")
    public String crearPartido(@ModelAttribute Partido partido) {
        partidoService.savePartido(partido);
        return "redirect:/admin/dashboard";
    }
}
