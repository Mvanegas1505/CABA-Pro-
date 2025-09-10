package com.CABA.CabaPro.controller;

import com.CABA.CabaPro.model.Partido;
import com.CABA.CabaPro.service.PartidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminPartidosController {

    @Autowired
    private PartidoService partidoService;

    @GetMapping("/admin/partidos")
    public String listarPartidos(Model model) {
    List<Partido> partidos = partidoService.getAllPartidos();
        model.addAttribute("partidos", partidos);
        return "admin/partidos";
    }
}
