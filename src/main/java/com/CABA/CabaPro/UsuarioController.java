package com.CABA.CabaPro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @PostMapping("/login-admin")
    public String loginAdmin(@RequestBody AdminLoginRequest req) {
        if (usuarioService.loginAdmin(req.getContrasena())) {
            return "Login admin exitoso";
        } else {
            return "ERROR: Contraseña de administrador incorrecta";
        }
    }

    public static class AdminLoginRequest {
        private String contrasena;
        public String getContrasena() { return contrasena; }
        public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    }
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/crear")
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        try {
            return usuarioService.crearUsuario(usuario.getNombre(), usuario.getContrasena());
        } catch (Exception e) {
            Usuario error = new Usuario();
            error.setNombre("ERROR: " + e.getMessage());
            return error;
        }
    }

    @PostMapping("/login")
    public Usuario login(@RequestBody Usuario usuario) {
        try {
            return usuarioService.login(usuario.getNombre(), usuario.getContrasena());
        } catch (Exception e) {
            Usuario error = new Usuario();
            error.setNombre("ERROR: " + e.getMessage());
            return error;
        }
    }

    @PostMapping("/cambiar-contrasena")
    public String cambiarContrasena(@RequestBody CambiarContrasenaRequest req) {
        try {
            usuarioService.cambiarContrasena(req.getNombre(), req.getContrasenaAntigua(), req.getContrasenaNueva());
            return "Contraseña cambiada correctamente";
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }

    public static class CambiarContrasenaRequest {
        private String nombre;
        private String contrasenaAntigua;
        private String contrasenaNueva;

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public String getContrasenaAntigua() { return contrasenaAntigua; }
        public void setContrasenaAntigua(String contrasenaAntigua) { this.contrasenaAntigua = contrasenaAntigua; }
        public String getContrasenaNueva() { return contrasenaNueva; }
        public void setContrasenaNueva(String contrasenaNueva) { this.contrasenaNueva = contrasenaNueva; }
    }
}
