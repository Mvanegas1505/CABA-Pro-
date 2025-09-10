package com.CABA.CabaPro.controller;

import com.CABA.CabaPro.dto.LoginRequestDTO;
import com.CABA.CabaPro.model.Usuario;
import com.CABA.CabaPro.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginRequestDTO", new LoginRequestDTO());
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequestDTO loginRequestDTO, Model model) {
        try {
            Usuario usuario = usuarioService.login(loginRequestDTO.getCorreo(), loginRequestDTO.getContrasena());
            if (usuario != null) {
                // Guardar usuario en sesión
                org.springframework.web.context.request.RequestAttributes requestAttributes = org.springframework.web.context.request.RequestContextHolder.currentRequestAttributes();
                jakarta.servlet.http.HttpSession session = (jakarta.servlet.http.HttpSession) requestAttributes.resolveReference(org.springframework.web.context.request.RequestAttributes.REFERENCE_SESSION);
                session.setAttribute("usuario", usuario);
                // Redirigir según el rol
                if (usuario.getRol().name().equals("ADMIN")) {
                    return "redirect:/admin/dashboard";
                } else {
                    return "redirect:/arbitro/dashboard";
                }
            } else {
                model.addAttribute("error", "Credenciales incorrectas");
                return "user/login";
            }
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "user/login";
        }
    }
}
