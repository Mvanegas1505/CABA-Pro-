package com.CABA.CabaPro.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.CABA.CabaPro.model.EscalafonEnum;
import com.CABA.CabaPro.model.Torneo;
import com.CABA.CabaPro.service.TarifaService;
import com.CABA.CabaPro.service.TorneoService;

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
                              @RequestParam("tarifaSegunda") Double tarifaSegunda,
                              RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("successMessage", "¡Torneo creado exitosamente!");

        // Guardar torneo
        Torneo torneoGuardado = torneoService.saveTorneo(torneo);

        // Delegar la creación de tarifas al servicio
        tarifaService.crearTarifa(torneoGuardado, EscalafonEnum.FIBA, tarifaFiba);
        tarifaService.crearTarifa(torneoGuardado, EscalafonEnum.PRIMERA, tarifaPrimera);
        tarifaService.crearTarifa(torneoGuardado, EscalafonEnum.SEGUNDA, tarifaSegunda);

        return "redirect:/admin/dashboard";
    }

    // Minimal delete endpoint for tournaments (used by admin UI)
    @DeleteMapping("/admin/torneos/{id}")
    @ResponseBody
    public Map<String, Object> eliminarTorneo(@PathVariable Long id) {
        Map<String, Object> resp = new HashMap<>();
        try {
            torneoService.deleteTorneo(id);
            resp.put("success", true);
            resp.put("message", "Torneo eliminado");
        } catch (Exception e) {
            resp.put("success", false);
            resp.put("message", e.getMessage());
        }
        return resp;
    }

    
}
