package com.CABA.CabaPro.controller.api;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.CABA.CabaPro.dto.api.ApiResponse;
import com.CABA.CabaPro.dto.api.arbitro.ArbitroProfileResponse;
import com.CABA.CabaPro.dto.api.arbitro.ArbitroUpdateRequest;
import com.CABA.CabaPro.dto.api.asignacion.AsignacionResponse;
import com.CABA.CabaPro.dto.api.liquidacion.LiquidacionDetailResponse;
import com.CABA.CabaPro.dto.api.liquidacion.LiquidacionSummaryResponse;
import com.CABA.CabaPro.model.Asignacion;
import com.CABA.CabaPro.model.EstadoAsignacionEnum;
import com.CABA.CabaPro.model.Liquidacion;
import com.CABA.CabaPro.model.Usuario;
import com.CABA.CabaPro.service.AsignacionService;
import com.CABA.CabaPro.service.LiquidacionService;
import com.CABA.CabaPro.service.UsuarioService;
import com.CABA.util.mapper.AsignacionMapper;
import com.CABA.util.mapper.LiquidacionMapper;
import com.CABA.util.mapper.UsuarioMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/arbitro")
@Validated
@Tag(name = "Arbitro")
public class ArbitroApiController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private AsignacionService asignacionService;
    @Autowired
    private LiquidacionService liquidacionService;

    private Usuario requireUser(HttpSession session) {
        // 1) Intentar sesión tradicional (form login)
        Usuario u = (Usuario) session.getAttribute("usuario");
        if (u != null) {
            Usuario full = usuarioService.findByCorreo(u.getCorreo());
            if (full != null)
                return full;
        }
        // 2) Intentar contexto de seguridad (JWT)
        var auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && auth.getName() != null) {
            Usuario full = usuarioService.findByCorreo(auth.getName());
            if (full != null)
                return full;
        }
        throw new IllegalStateException("No autenticado. Inicie sesión o envíe un token válido");
    }

    @GetMapping("/me")
    @Operation(summary = "Obtiene el perfil del árbitro autenticado")
    public ResponseEntity<ApiResponse> me(HttpSession session) {
        Usuario u = requireUser(session);
        ArbitroProfileResponse dto = UsuarioMapper.toProfile(u);
        return ResponseEntity.ok(new ApiResponse(true, "Perfil cargado", dto, HttpStatus.OK.value()));
    }

    @PutMapping("/me")
    @Operation(summary = "Actualiza el perfil del árbitro autenticado")
    public ResponseEntity<ApiResponse> updateMe(
            HttpSession session,
            @Valid @RequestBody ArbitroUpdateRequest body) {
        Usuario u = requireUser(session);
        UsuarioMapper.applyUpdate(u, body);
        usuarioService.guardar(u);
        ArbitroProfileResponse dto = UsuarioMapper.toProfile(u);
        return ResponseEntity.ok(new ApiResponse(true, "Perfil actualizado", dto, HttpStatus.OK.value()));
    }

    @GetMapping("/asignaciones")
    @Operation(summary = "Lista asignaciones del árbitro por estado (pendiente/aceptada). Si no se envía estado, lista pendientes.")
    public ResponseEntity<ApiResponse> misAsignaciones(
            HttpSession session,
            @RequestParam(name = "estado", required = false) String estado) {
        Usuario u = requireUser(session);
        List<Asignacion> asignaciones;
        EstadoAsignacionEnum target = null;
        if (estado != null) {
            try {
                target = EstadoAsignacionEnum.valueOf(estado.toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("Estado inválido. Use PENDIENTE o ACEPTADA o RECHAZADA");
            }
        }
        if (target == null || target == EstadoAsignacionEnum.PENDIENTE) {
            asignaciones = asignacionService.getAsignacionesPendientesPorArbitro(u.getCorreo());
        } else if (target == EstadoAsignacionEnum.ACEPTADA) {
            asignaciones = asignacionService.getAsignacionesAceptadasPorArbitro(u.getCorreo());
        } else {
            // RECHAZADA no tiene método dedicado; filtrar manual si fuera necesario
            asignaciones = asignacionService.getAllAsignaciones().stream()
                    .filter(a -> a.getArbitro() != null && Objects.equals(a.getArbitro().getCorreo(), u.getCorreo()))
                    .filter(a -> a.getEstado() == EstadoAsignacionEnum.RECHAZADA)
                    .collect(Collectors.toList());
        }
        List<AsignacionResponse> out = asignaciones.stream().map(AsignacionMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse(true, "OK", out, HttpStatus.OK.value()));
    }

    @PostMapping("/asignaciones/{id}/aceptar")
    @Operation(summary = "Acepta una asignación del árbitro autenticado")
    public ResponseEntity<ApiResponse> aceptar(
            HttpSession session,
            @PathVariable Long id) {
        Usuario u = requireUser(session);
        var opt = asignacionService.aceptarAsignacion(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Asignación no encontrada", null, HttpStatus.NOT_FOUND.value()));
        }
        var a = opt.get();
        if (a.getArbitro() == null || !u.getCorreo().equals(a.getArbitro().getCorreo())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse(false, "La asignación no pertenece al usuario", null,
                            HttpStatus.FORBIDDEN.value()));
        }
        return ResponseEntity.ok(
                new ApiResponse(true, "Asignación aceptada", AsignacionMapper.toResponse(a), HttpStatus.OK.value()));
    }

    @PostMapping("/asignaciones/{id}/rechazar")
    @Operation(summary = "Rechaza una asignación del árbitro autenticado")
    public ResponseEntity<ApiResponse> rechazar(
            HttpSession session,
            @PathVariable Long id) {
        Usuario u = requireUser(session);
        var opt = asignacionService.rechazarAsignacion(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Asignación no encontrada", null, HttpStatus.NOT_FOUND.value()));
        }
        var a = opt.get();
        if (a.getArbitro() == null || !u.getCorreo().equals(a.getArbitro().getCorreo())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse(false, "La asignación no pertenece al usuario", null,
                            HttpStatus.FORBIDDEN.value()));
        }
        return ResponseEntity.ok(
                new ApiResponse(true, "Asignación rechazada", AsignacionMapper.toResponse(a), HttpStatus.OK.value()));
    }

    @GetMapping("/liquidaciones")
    @Operation(summary = "Lista las liquidaciones del árbitro autenticado")
    public ResponseEntity<ApiResponse> misLiquidaciones(HttpSession session) {
        Usuario u = requireUser(session);
        List<LiquidacionSummaryResponse> out = liquidacionService.getLiquidacionesPorArbitro(u.getCorreo()).stream()
                .map(LiquidacionMapper::toSummary)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse(true, "OK", out, HttpStatus.OK.value()));
    }

    @GetMapping("/liquidaciones/{id}")
    @Operation(summary = "Obtiene detalle de una liquidación del árbitro autenticado")
    public ResponseEntity<ApiResponse> liquidacionDetalle(
            HttpSession session,
            @PathVariable Long id) {
        Usuario u = requireUser(session);
        var opt = liquidacionService.getLiquidacionById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Liquidación no encontrada", null, HttpStatus.NOT_FOUND.value()));
        }
        var l = opt.get();
        if (l.getUsuario() == null || !u.getCorreo().equals(l.getUsuario().getCorreo())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse(false, "La liquidación no pertenece al usuario", null,
                            HttpStatus.FORBIDDEN.value()));
        }
        return ResponseEntity.ok(new ApiResponse(true, "OK", LiquidacionMapper.toDetail(l), HttpStatus.OK.value()));
    }

    @GetMapping(value = "/liquidaciones/{id}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @Operation(summary = "Descarga el PDF de una liquidación del árbitro autenticado")
    public ResponseEntity<byte[]> liquidacionPdf(HttpSession session, @PathVariable Long id) {
        Usuario u = requireUser(session);
        Liquidacion l = liquidacionService.getLiquidacionById(id).orElse(null);
        if (l == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new byte[0]);
        if (l.getUsuario() == null || !u.getCorreo().equals(l.getUsuario().getCorreo())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new byte[0]);
        }
        byte[] pdf = liquidacionService.generarPDF(l);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=liquidacion-" + id + ".pdf");
        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }
}
