package com.CABA.CabaPro.controller;

import com.CABA.CabaPro.model.Asignacion;
import com.CABA.CabaPro.model.EstadoAsignacionEnum;
import com.CABA.CabaPro.model.Usuario;
import com.CABA.CabaPro.service.AsignacionService;
import com.CABA.CabaPro.service.PartidoService;
import com.CABA.CabaPro.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ArbitroController {

    @Autowired
    private AsignacionService asignacionService;

    @Autowired
    private PartidoService partidoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/arbitro/dashboard")
    public String dashboard(Model model, HttpSession session) {
    // Obtener el árbitro desde la sesión (guardado en login)
    Usuario arbitroSesion = (Usuario) session.getAttribute("usuario");
    Usuario arbitro = null;
    if (arbitroSesion != null && arbitroSesion.getCorreo() != null) {
        // Consultar el usuario completo por correo
        arbitro = usuarioService.getUsuarioByCorreo(arbitroSesion.getCorreo());
    }
    final Usuario arbitroFinal = arbitro;

    if (arbitroFinal != null) {
        List<Asignacion> todasAsignaciones = asignacionService.getAllAsignaciones();

        // Filtrar asignaciones de este árbitro por correo
        List<Asignacion> asignacionesArbitro = todasAsignaciones.stream()
            .filter(a -> a.getArbitro() != null && a.getArbitro().getId().equals(arbitroFinal.getId()))
            .collect(Collectors.toList());

        // Separar por estado
        List<Asignacion> asignacionesPendientes = asignacionesArbitro.stream()
            .filter(a -> a.getEstado() == EstadoAsignacionEnum.PENDIENTE)
            .collect(Collectors.toList());

        List<Asignacion> asignacionesAceptadas = asignacionesArbitro.stream()
            .filter(a -> a.getEstado() == EstadoAsignacionEnum.ACEPTADA)
            .collect(Collectors.toList());

        model.addAttribute("arbitro", arbitroFinal);
        model.addAttribute("asignacionesPendientes", asignacionesPendientes);
        model.addAttribute("asignacionesAceptadas", asignacionesAceptadas);
        model.addAttribute("totalPendientes", asignacionesPendientes.size());
        model.addAttribute("totalAceptadas", asignacionesAceptadas.size());
    }

    return "arbitro/dashboard";
    }

    @PostMapping("/arbitro/aceptar-asignacion/{id}")
    public String aceptarAsignacion(@PathVariable Long id) {
        asignacionService.aceptarAsignacion(id);
        return "redirect:/arbitro/dashboard";
    }

    @PostMapping("/arbitro/rechazar-asignacion/{id}")
    public String rechazarAsignacion(@PathVariable Long id) {
        asignacionService.rechazarAsignacion(id);
        return "redirect:/arbitro/dashboard";
    }
}
