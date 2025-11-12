package com.CABA.CabaPro.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.CABA.CabaPro.model.Partido;
import com.CABA.CabaPro.service.PartidoService;

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

    // Minimal delete endpoint for admin UI to remove a partido by id
    @DeleteMapping("/admin/partidos/{id}")
    @ResponseBody
    public Map<String, Object> eliminarPartido(@PathVariable Long id) {
        Map<String, Object> resp = new HashMap<>();
        try {
            partidoService.deletePartido(id);
            resp.put("success", true);
            resp.put("message", "Partido eliminado");
        } catch (Exception e) {
            resp.put("success", false);
            resp.put("message", e.getMessage());
        }
        return resp;
    }
}
