package com.CABA.CabaPro.controller.api;

import com.CABA.CabaPro.dto.api.ApiResponse;
import com.CABA.CabaPro.dto.api.arbitro.ArbitroProfileResponse;
import com.CABA.CabaPro.dto.api.auth.AuthResponse;
import com.CABA.CabaPro.dto.api.auth.LoginRequest;
import com.CABA.CabaPro.dto.api.auth.RegisterRequest;
import com.CABA.CabaPro.model.RolEnum;
import com.CABA.CabaPro.model.Usuario;
import com.CABA.CabaPro.security.JwtService;
import com.CABA.CabaPro.service.UsuarioService;
import com.CABA.util.mapper.UsuarioMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth API")
public class AuthApiController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthApiController(UsuarioService usuarioService, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    @Operation(summary = "Login REST para árbitros (JWT)")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest body) {
        Usuario u = usuarioService.findByCorreo(body.getCorreo());
        if (u == null || u.getContrasena() == null
                || !passwordEncoder.matches(body.getContrasena(), u.getContrasena())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error(null, "Credenciales inválidas", 401));
        }
        String token = jwtService.generateToken(u.getCorreo(),
                java.util.Map.of("role", (u.getRol() != null ? u.getRol().name() : "ARBITRO")));
        ArbitroProfileResponse profile = UsuarioMapper.toProfile(u);
        return ResponseEntity.ok(ApiResponse.success(new AuthResponse(token, profile), "Login exitoso"));
    }

    @PostMapping("/register")
    @Operation(summary = "Registro REST de árbitros (JWT)")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest body) {
        Usuario exists = usuarioService.findByCorreo(body.getCorreo());
        if (exists != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ApiResponse.error(null, "El correo ya está registrado", 409));
        }
        Usuario u = new Usuario();
        u.setCorreo(body.getCorreo());
        u.setNombre(body.getNombre());
        u.setEspecialidad(body.getEspecialidad());
        u.setEscalafon(body.getEscalafon());
        u.setRol(RolEnum.ARBITRO);
        u.setContrasena(passwordEncoder.encode(body.getContrasena()));
        usuarioService.guardar(u);

        String token = jwtService.generateToken(u.getCorreo(), java.util.Map.of("role", "ARBITRO"));
        ArbitroProfileResponse profile = UsuarioMapper.toProfile(u);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(new AuthResponse(token, profile), "Registro exitoso"));
    }
}
