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
    if (arbitroSesion != null && arbitroSesion.getCorreo() != null) {
        // Consultar el usuario completo por correo
        Usuario arbitro = usuarioService.getUsuarioByCorreo(arbitroSesion.getCorreo());
        if (arbitro != null) {
            List<Asignacion> asignacionesPendientes = asignacionService.getAsignacionesPendientesPorArbitro(arbitro.getCorreo());
            List<Asignacion> asignacionesAceptadas = asignacionService.getAsignacionesAceptadasPorArbitro(arbitro.getCorreo());

            model.addAttribute("arbitro", arbitro);
            model.addAttribute("asignacionesPendientes", asignacionesPendientes);
            model.addAttribute("asignacionesAceptadas", asignacionesAceptadas);
            model.addAttribute("totalPendientes", asignacionesPendientes.size());
            model.addAttribute("totalAceptadas", asignacionesAceptadas.size());
            model.addAttribute("misPartidos", asignacionesAceptadas);
        }
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

    @GetMapping("/arbitro/partidos")
    public String verPartidosAceptados(HttpSession session, Model model) {
        Usuario arbitro = (Usuario) session.getAttribute("usuario");
        if (arbitro == null) {
            return "redirect:/login";
        }
        List<Asignacion> misPartidos = asignacionService.getAsignacionesAceptadasPorArbitro(arbitro.getCorreo());
        model.addAttribute("arbitro", arbitro);
        model.addAttribute("misPartidos", misPartidos);
        return "arbitro/partidos";
    }
}
