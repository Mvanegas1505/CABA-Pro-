package com.CABA.CabaPro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
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
