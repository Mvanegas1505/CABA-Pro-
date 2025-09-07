
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
            // Retornar usuario vacío con mensaje de error en nombre
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
}
