package com.CABA.CabaPro;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

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

    public boolean cambiarContrasena(String nombre, String contrasenaAntigua, String contrasenaNueva) throws Exception {
        for (Usuario u : usuarios) {
            if (u.getNombre().equals(nombre)) {
                if (!u.getContrasena().equals(contrasenaAntigua)) {
                    throw new Exception("La contraseña antigua es incorrecta");
                }
                u.setContrasena(contrasenaNueva);
                return true;
            }
        }
        throw new Exception("Usuario no encontrado");
    }
}
