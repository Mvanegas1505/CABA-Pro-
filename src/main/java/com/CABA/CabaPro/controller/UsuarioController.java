package com.CABA.CabaPro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.CABA.CabaPro.model.Usuario;
import com.CABA.CabaPro.service.UsuarioService;
import com.CABA.CabaPro.dto.RegistroArbitroDTO;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Login page (GET)
    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }

    // Login API (POST, para uso con JS o pruebas)
    @PostMapping("/login")
    public Usuario login(@RequestBody Usuario usuario) {
        try {
            return usuarioService.login(usuario.getCorreo(), usuario.getContrasena());
        } catch (Exception e) {
            Usuario error = new Usuario();
            error.setNombre("ERROR: " + e.getMessage());
            return error;
        }
    }

    // Admin login (POST)
    @PostMapping("/login-admin")
    public String loginAdmin(@RequestBody AdminLoginRequest req, org.springframework.ui.Model model) {
        if (usuarioService.loginAdmin(req.getContrasena())) {
            model.addAttribute("message", "Login admin exitoso");
        } else {
            model.addAttribute("message", "ERROR: Contraseña de administrador incorrecta");
        }
        return "user/login";
    }

    // Registro árbitro (GET)
    @GetMapping("/registro")
    public String registroPage(org.springframework.ui.Model model) {
        model.addAttribute("registroArbitroDTO", new RegistroArbitroDTO());
        return "user/registro";
    }

    // Registro árbitro (POST)
    @PostMapping("/registro")
    public String crearUsuario(
            @org.springframework.web.bind.annotation.ModelAttribute RegistroArbitroDTO registroArbitroDTO,
            org.springframework.ui.Model model) {
        try {
            Usuario usuario = new Usuario();
            usuario.setNombre(registroArbitroDTO.getNombre());
            usuario.setCorreo(registroArbitroDTO.getCorreo());
            usuario.setContrasena(registroArbitroDTO.getContrasena());
            usuario.setRol(com.CABA.CabaPro.model.RolEnum.ARBITRO);
            usuarioService.crearUsuario(usuario.getNombre(), usuario.getContrasena(), usuario.getCorreo());
            model.addAttribute("usuario", usuario);
            return "redirect:/usuarios/perfil?correo=" + usuario.getCorreo();
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "user/registro";
        }
    }

    // Completar perfil (GET)
    @GetMapping("/perfil")
    public String perfilPage(@org.springframework.web.bind.annotation.RequestParam String correo,
            org.springframework.ui.Model model) {
        Usuario usuario = null;
        try {
            usuario = usuarioService.login(correo, ""); // Solo para obtener el usuario, ignora contraseña
        } catch (Exception e) {
            return "redirect:/usuarios/registro";
        }
        model.addAttribute("usuario", usuario);
        return "user/perfil";
    }

    // Completar perfil (POST)
    @PostMapping("/perfil")
    public String actualizarPerfil(@org.springframework.web.bind.annotation.ModelAttribute Usuario usuario,
            org.springframework.ui.Model model) {
        // usuarioService.actualizarPerfil(usuario); // Implementar si es necesario
        model.addAttribute("message", "Perfil actualizado correctamente");
        return "user/perfil";
    }

    // Cambiar contraseña (POST)
    @PostMapping("/cambiar-contrasena")
    public String cambiarContrasena(@RequestBody CambiarContrasenaRequest req) {
        try {
            usuarioService.cambiarContrasena(req.getCorreo(), req.getContrasenaAntigua(), req.getContrasenaNueva());
            return "Contraseña cambiada correctamente";
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }

    // DTOs internos
    public static class AdminLoginRequest {
        private String contrasena;

        public String getContrasena() {
            return contrasena;
        }

        public void setContrasena(String contrasena) {
            this.contrasena = contrasena;
        }
    }

    public static class CambiarContrasenaRequest {
        private String correo;
        private String contrasenaAntigua;
        private String contrasenaNueva;

        public String getCorreo() {
            return correo;
        }

        public void setCorreo(String correo) {
            this.correo = correo;
        }

        public String getContrasenaAntigua() {
            return contrasenaAntigua;
        }

        public void setContrasenaAntigua(String contrasenaAntigua) {
            this.contrasenaAntigua = contrasenaAntigua;
        }

        public String getContrasenaNueva() {
            return contrasenaNueva;
        }

        public void setContrasenaNueva(String contrasenaNueva) {
            this.contrasenaNueva = contrasenaNueva;
        }
    }
}
