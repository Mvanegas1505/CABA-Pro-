package com.CABA.CabaPro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.CABA.CabaPro.model.Usuario;
import com.CABA.CabaPro.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

import com.CABA.CabaPro.dto.RegistroArbitroDTO;

@Controller

public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

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
            // Verificar si el correo ya existe
            if (usuarioService.findByCorreo(registroArbitroDTO.getCorreo()) != null) {
                model.addAttribute("error", "El correo ya está registrado. Por favor usa otro correo.");
                return "user/registro";
            }
            Usuario usuario = new Usuario();
            usuario.setNombre(registroArbitroDTO.getNombre());
            usuario.setCorreo(registroArbitroDTO.getCorreo());
            usuario.setContrasena(registroArbitroDTO.getContrasena());
            usuario.setRol(com.CABA.CabaPro.model.RolEnum.ARBITRO);
               usuario.setEspecialidad(null); // Inicializa como null
            usuarioService.crearUsuario(usuario.getNombre(), usuario.getContrasena(), usuario.getCorreo());
            model.addAttribute("usuario", usuario);
            return "redirect:/perfil?correo=" + usuario.getCorreo();
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "user/registro";
        }
    }

    // Completar perfil (GET)
    @GetMapping("/perfil")
    public String perfilPage(@org.springframework.web.bind.annotation.RequestParam String correo,
            org.springframework.ui.Model model) {
        Usuario usuario = usuarioService.findByCorreo(correo);
        if (usuario == null) {
            return "redirect:/registro";
        }
        model.addAttribute("usuario", usuario);
        return "user/perfil";
    }

    // Completar perfil (POST)
    @PostMapping("/perfil")
    public String actualizarPerfil(@org.springframework.web.bind.annotation.ModelAttribute Usuario usuario,
            org.springframework.ui.Model model) {
        // Buscar el usuario real por correo
        Usuario usuarioDB = usuarioService.findByCorreo(usuario.getCorreo());
        if (usuarioDB != null) {
            usuarioDB.setEspecialidad(usuario.getEspecialidad());
            usuarioDB.setEscalafon(usuario.getEscalafon());
            usuarioDB.setFotoPerfilUrl(usuario.getFotoPerfilUrl());
            usuarioService.guardar(usuarioDB);
        }
        return "redirect:/arbitro/dashboard";
    }

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
