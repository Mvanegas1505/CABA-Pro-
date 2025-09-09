package com.CABA.CabaPro.controller;

import com.CABA.CabaPro.model.EscalafonEnum;
import com.CABA.CabaPro.model.Tarifa;
import com.CABA.CabaPro.model.Torneo;
import com.CABA.CabaPro.service.TarifaService;
import com.CABA.CabaPro.service.TorneoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TorneoController {

    @Autowired
    private TorneoService torneoService;

    @Autowired
    private TarifaService tarifaService;

    @GetMapping("/admin/crear-torneo")
    public String mostrarFormularioCrearTorneo(Model model) {
        model.addAttribute("torneo", new Torneo());
        model.addAttribute("escalafones", EscalafonEnum.values());
        return "admin/crear-torneo";
    }

    @PostMapping("/admin/crear-torneo")
    public String crearTorneo(@ModelAttribute Torneo torneo,
                              @RequestParam("tarifaFiba") Double tarifaFiba,
                              @RequestParam("tarifaPrimera") Double tarifaPrimera,
                              @RequestParam("tarifaSegunda") Double tarifaSegunda) {

        // Guardar torneo
        Torneo torneoGuardado = torneoService.saveTorneo(torneo);

        // Delegar la creación de tarifas al servicio
        tarifaService.crearTarifa(torneoGuardado, EscalafonEnum.FIBA, tarifaFiba);
        tarifaService.crearTarifa(torneoGuardado, EscalafonEnum.PRIMERA, tarifaPrimera);
        tarifaService.crearTarifa(torneoGuardado, EscalafonEnum.SEGUNDA, tarifaSegunda);

        return "redirect:/admin/dashboard";
    }
}
