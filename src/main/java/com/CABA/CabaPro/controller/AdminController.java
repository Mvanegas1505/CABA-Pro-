package com.CABA.CabaPro.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.CABA.CabaPro.model.Asignacion;
import com.CABA.CabaPro.model.EspecialidadEnum;
import com.CABA.CabaPro.model.EstadoAsignacionEnum;
import com.CABA.CabaPro.model.Partido;
import com.CABA.CabaPro.model.Torneo;
import com.CABA.CabaPro.model.Usuario;
import com.CABA.CabaPro.service.AsignacionService;
import com.CABA.CabaPro.service.PartidoService;
import com.CABA.CabaPro.service.TorneoService;
import com.CABA.CabaPro.service.UsuarioService;

@Controller
public class AdminController {
    // ==============================================
    // Remover asignación
    // ==============================================
    @org.springframework.web.bind.annotation.DeleteMapping("/admin/remover-asignacion/{id}")
    @ResponseBody
    public Map<String, Object> removerAsignacion(@org.springframework.web.bind.annotation.PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            asignacionService.deleteAsignacion(id);
            response.put("success", true);
            response.put("message", "Asignación eliminada correctamente");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar la asignación: " + e.getMessage());
        }
        return response;
    }

    @Autowired
    private PartidoService partidoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AsignacionService asignacionService;

    @Autowired
    private TorneoService torneoService;

    @Autowired
    private javax.sql.DataSource dataSource;

    // ==============================================
    // Asignar árbitro a un partido
    // ==============================================
    @PostMapping("/admin/asignar-arbitro")
    @ResponseBody
    public Map<String, Object> asignarArbitro(@RequestBody Map<String, Object> payload) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Validar parámetros de entrada
            if (!payload.containsKey("partidoId") || !payload.containsKey("arbitroCorreo")) {
                response.put("success", false);
                response.put("message", "Faltan parámetros requeridos");
                return response;
            }

            Long partidoId = Long.valueOf(payload.get("partidoId").toString());
            String arbitroCorreo = payload.get("arbitroCorreo").toString();

            Optional<Partido> partidoOpt = partidoService.getPartidoById(partidoId);
            Usuario arbitro = usuarioService.findByCorreo(arbitroCorreo);

            if (partidoOpt.isEmpty() || arbitro == null) {
                response.put("success", false);
                response.put("message", "Partido o árbitro no encontrado");
                return response;
            }

            Partido partido = partidoOpt.get();

            // Validar que el usuario sea árbitro
            if (arbitro.getRol() == null || !arbitro.getRol().name().equals("ARBITRO")) {
                response.put("success", false);
                response.put("message", "El usuario seleccionado no es un árbitro");
                return response;
            }

            EspecialidadEnum especialidad;
            try {
                especialidad = EspecialidadEnum.valueOf(arbitro.getEspecialidad().toUpperCase());
            } catch (IllegalArgumentException e) {
                response.put("success", false);
                response.put("message", "Especialidad inválida");
                return response;
            }

            // Verificar si ya hay árbitro asignado con esa especialidad
            boolean yaAsignado = partido.getAsignaciones().stream()
                    .anyMatch(a -> a.getEspecialidad() == especialidad);

            if (yaAsignado) {
                response.put("success", false);
                response.put("message", "Ya existe un árbitro asignado para esa especialidad");
                return response;
            }

            // Crear asignación
            Asignacion asignacion = new Asignacion();
            asignacion.setArbitro(arbitro);
            asignacion.setPartido(partido);
            asignacion.setEspecialidad(especialidad);
            asignacion.setEstado(EstadoAsignacionEnum.PENDIENTE);

            asignacionService.saveAsignacion(asignacion);

            response.put("success", true);
            response.put("message", "Árbitro asignado correctamente");
            return response;

        } catch (NumberFormatException e) {
            response.put("success", false);
            response.put("message", "ID de partido o árbitro inválido");
            return response;
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al procesar la asignación: " + e.getMessage());
            return response;
        }
    }

    // ==============================================
    // Dashboard
    // ==============================================
    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        List<Torneo> torneos = torneoService.getAllTorneos();
        List<Partido> partidos = partidoService.getAllPartidos();
        List<Asignacion> asignaciones = asignacionService.getAllAsignaciones();
        List<Usuario> arbitros = usuarioService.getArbitros(); // Nuevo método recomendado

        // Torneos activos
        long torneosActivos = torneos.stream()
                .filter(t -> t.getFechaFin() != null && !t.getFechaFin().isBefore(LocalDate.now()))
                .count();

        // Partidos de hoy
        long partidosHoy = partidos.stream()
                .filter(p -> p.getFecha() != null && p.getFecha().isEqual(LocalDate.now()))
                .count();

        // Árbitros activos
        long arbitrosActivos = arbitros.size();

        // Asignaciones pendientes
        long asignacionesPendientes = asignaciones.stream()
                .filter(a -> a.getEstado() == EstadoAsignacionEnum.PENDIENTE)
                .count();

        model.addAttribute("torneos", torneos);
        model.addAttribute("partidos", partidos);
        model.addAttribute("asignaciones", asignaciones);
        model.addAttribute("arbitros", arbitros);
        model.addAttribute("torneosActivos", torneosActivos);
        model.addAttribute("partidosHoy", partidosHoy);
        model.addAttribute("arbitrosActivos", arbitrosActivos);
        model.addAttribute("asignacionesPendientes", asignacionesPendientes);

        return "admin/dashboard";
    }

    // ==============================================
    // Gestionar árbitros
    // ==============================================
    @GetMapping("/admin/arbitros")
    public String gestionarArbitros(Model model) {
        List<Partido> partidos = partidoService.getAllPartidos();
        List<Usuario> arbitros = usuarioService.getArbitros(); // Nuevo método recomendado
        List<Torneo> torneos = torneoService.getAllTorneos();

        // Disponibilidad de árbitros por especialidad y partido
        Map<Long, Map<EspecialidadEnum, List<Usuario>>> arbitrosDisponiblesPorPartido = new HashMap<>();
        for (Partido partido : partidos) {
            Map<EspecialidadEnum, List<Usuario>> porEspecialidad = new HashMap<>();
            for (EspecialidadEnum especialidad : EspecialidadEnum.values()) {
                List<Usuario> disponibles = arbitros.stream()
                        .filter(a -> a.getEspecialidad() != null &&
                                a.getEspecialidad().equalsIgnoreCase(especialidad.name()))
                        .filter(a -> partido.getAsignaciones().stream()
                                .noneMatch(asig -> asig.getEspecialidad() == especialidad))
                        .collect(Collectors.toList());
                porEspecialidad.put(especialidad, disponibles);
            }
            arbitrosDisponiblesPorPartido.put(partido.getId(), porEspecialidad);
        }

        model.addAttribute("partidos", partidos);
        model.addAttribute("arbitros", arbitros);
        model.addAttribute("torneos", torneos);
        model.addAttribute("arbitrosDisponiblesPorPartido", arbitrosDisponiblesPorPartido);

        return "admin/gestionar-arbitros";
    }

    // Endpoint de diagnóstico: devuelve JSON con árbitros (correo, nombre, rol, especialidad, escalafon)
    @GetMapping("/admin/api/arbitros")
    @ResponseBody
    public java.util.List<java.util.Map<String, Object>> apiArbitros() {
        java.util.List<Usuario> arbitros = usuarioService.getArbitros();
        return arbitros.stream().map(u -> {
            java.util.Map<String, Object> m = new java.util.HashMap<>();
            m.put("correo", u.getCorreo());
            m.put("nombre", u.getNombre());
            m.put("rol", u.getRol() != null ? u.getRol().name() : null);
            m.put("especialidad", u.getEspecialidad());
            m.put("escalafon", u.getEscalafon());
            return m;
        }).toList();
    }

    // Endpoint de diagnóstico: devuelve TODOS los usuarios (correo, nombre, rol, especialidad, escalafon)
    @GetMapping("/admin/api/usuarios")
    @ResponseBody
    public java.util.List<java.util.Map<String, Object>> apiUsuarios() {
        java.util.List<Usuario> usuarios = usuarioService.getAllUsuarios();
        return usuarios.stream().map(u -> {
            java.util.Map<String, Object> m = new java.util.HashMap<>();
            m.put("correo", u.getCorreo());
            m.put("nombre", u.getNombre());
            m.put("rol", u.getRol() != null ? u.getRol().name() : null);
            m.put("especialidad", u.getEspecialidad());
            m.put("escalafon", u.getEscalafon());
            return m;
        }).toList();
    }

    // Temporary migration endpoint: backup DB files and mark users as ARBITRO when they have
    // especialidad or escalafon but no role set. This is a one-click, non-destructive migration
    // (creates backups). Call only once and remove after use.
    @PostMapping("/admin/api/migrate-arbitros")
    @ResponseBody
    public java.util.Map<String, Object> migrateArbitros() {
        java.util.Map<String, Object> resp = new java.util.HashMap<>();
        java.util.List<String> backups = new java.util.ArrayList<>();
        java.util.List<String> updated = new java.util.ArrayList<>();

        // Create SQL script backup using H2 SCRIPT command via existing DataSource connection
        try {
            java.nio.file.Path cwd = java.nio.file.Paths.get(System.getProperty("user.dir"));
            java.nio.file.Path backupDir = cwd.resolve("data-backups");
            if (!java.nio.file.Files.exists(backupDir)) {
                java.nio.file.Files.createDirectories(backupDir);
            }
            String time = java.time.LocalDateTime.now().toString().replace(':', '-');
            java.nio.file.Path scriptFile = backupDir.resolve("testdb.script." + time + ".sql");

            try (java.sql.Connection conn = dataSource.getConnection(); java.sql.Statement st = conn.createStatement()) {
                // H2 supports: SCRIPT TO 'filename'
                String sql = "SCRIPT TO '" + scriptFile.toString().replace("\\", "/") + "'";
                st.execute(sql);
                backups.add(scriptFile.toString());
            }
        } catch (Exception e) {
            resp.put("success", false);
            resp.put("message", "Error creating SQL script backup: " + e.getMessage());
            return resp;
        }

        // Perform migration using usuarioService
        try {
            java.util.List<Usuario> usuarios = usuarioService.getAllUsuarios();
            for (Usuario u : usuarios) {
                boolean hasEspecialidad = u.getEspecialidad() != null && !u.getEspecialidad().trim().isEmpty();
                boolean hasEscalafon = u.getEscalafon() != null && !u.getEscalafon().trim().isEmpty();
                boolean isArbitro = u.getRol() != null && u.getRol().name().equals("ARBITRO");
                if ((hasEspecialidad || hasEscalafon) && !isArbitro) {
                    u.setRol(com.CABA.CabaPro.model.RolEnum.ARBITRO);
                    usuarioService.guardar(u);
                    updated.add(u.getCorreo());
                }
            }
        } catch (Exception e) {
            resp.put("success", false);
            resp.put("message", "Error during migration: " + e.getMessage());
            resp.put("backups", backups);
            return resp;
        }

        resp.put("success", true);
        resp.put("backups", backups);
        resp.put("updatedCount", updated.size());
        resp.put("updated", updated);
        return resp;
    }
}