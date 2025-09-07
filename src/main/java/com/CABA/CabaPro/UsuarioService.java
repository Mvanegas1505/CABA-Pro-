
package com.CABA.CabaPro;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {
    private List<Usuario> usuarios = new ArrayList<>();
    private Long nextId = 1L;

    public Usuario crearUsuario(String nombre, String contrasena) throws Exception {
        for (Usuario u : usuarios) {
            if (u.getNombre().equals(nombre)) {
                throw new Exception("El nombre de usuario ya existe");
            }
        }
        Usuario usuario = new Usuario();
        usuario.setId(nextId++);
        usuario.setNombre(nombre);
        usuario.setContrasena(contrasena);
        usuarios.add(usuario);
        return usuario;
    }

    public Usuario login(String nombre, String contrasena) throws Exception {
        for (Usuario u : usuarios) {
            if (u.getNombre().equals(nombre) && u.getContrasena().equals(contrasena)) {
                return u;
            }
        }
        throw new Exception("Usuario o contraseña incorrectos");
    }
}
