package com.CABA.CabaPro.controller;

import com.CABA.CabaPro.model.Asignacion;
import com.CABA.CabaPro.model.Partido;
import com.CABA.CabaPro.model.Torneo;
import com.CABA.CabaPro.service.AsignacionService;
import com.CABA.CabaPro.service.PartidoService;
import com.CABA.CabaPro.service.TorneoService;
import com.CABA.CabaPro.service.UsuarioService;
import com.CABA.CabaPro.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    @Autowired
    private TorneoService torneoService;

    @Autowired
    private PartidoService partidoService;

    @Autowired
    private AsignacionService asignacionService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        List<Torneo> torneos = torneoService.getAllTorneos();
        List<Partido> partidos = partidoService.getAllPartidos();
        List<Asignacion> asignaciones = asignacionService.getAllAsignaciones();
        List<Usuario> arbitros = usuarioService.getAllUsuarios().stream()
            .filter(u -> u.getRol() != null && u.getRol().name().equals("ARBITRO"))
            .collect(Collectors.toList());
        model.addAttribute("arbitros", arbitros);

        // Torneos activos: los que la fecha fin es hoy o en el futuro
        long torneosActivos = torneos.stream()
                .filter(t -> t.getFechaFin() != null && !t.getFechaFin().isBefore(java.time.LocalDate.now()))
                .count();

        // Partidos hoy: partidos cuya fecha es hoy
        long partidosHoy = partidos.stream()
                .filter(p -> p.getFecha() != null && p.getFecha().isEqual(java.time.LocalDate.now()))
                .count();

        // Árbitros activos: solo usuarios con rol ARBITRO
        long arbitrosActivos = usuarioService.getAllUsuarios().stream()
                .filter(u -> u.getRol() != null && u.getRol().name().equals("ARBITRO"))
                .count();

        // Asignaciones pendientes: asignaciones con estado PENDIENTE
        long asignacionesPendientes = asignaciones.stream()
                .filter(a -> a.getEstado() != null && a.getEstado().name().equals("PENDIENTE"))
                .count();

        model.addAttribute("torneos", torneos);
        model.addAttribute("partidos", partidos);
        model.addAttribute("asignaciones", asignaciones);
        model.addAttribute("torneosActivos", torneosActivos);
        model.addAttribute("partidosHoy", partidosHoy);
        model.addAttribute("arbitrosActivos", arbitrosActivos);
        model.addAttribute("asignacionesPendientes", asignacionesPendientes);

        return "admin/dashboard";
    }

    @GetMapping("/admin/arbitros")
    public String gestionarArbitros(Model model) {

        List<Partido> partidos = partidoService.getAllPartidos();
        List<Usuario> usuarios = usuarioService.getAllUsuarios().stream()
                .filter(u -> u.getRol() != null && u.getRol().name().equals("ARBITRO"))
                .collect(Collectors.toList());
        List<Torneo> torneos = torneoService.getAllTorneos();

        model.addAttribute("partidos", partidos);
        model.addAttribute("Usuarios", usuarios);
        model.addAttribute("torneos", torneos);

        return "admin/gestionar-arbitros";
    }
}