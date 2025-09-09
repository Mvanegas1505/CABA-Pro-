package com.CABA.CabaPro.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.CABA.CabaPro.model.Usuario;

@Service
public class UsuarioService {
    private List<Usuario> usuarios = new ArrayList<>();
    private Long nextId = 1L;
    private final String ADMIN_PASSWORD = "ADMIN1";

    public boolean loginAdmin(String contrasena) {
        return ADMIN_PASSWORD.equals(contrasena);
    }

    public Usuario crearUsuario(String nombre, String contrasena, String email) throws Exception {
        for (Usuario u : usuarios) {
            if (u.getNombre().equals(nombre)) {
                throw new Exception("El nombre de usuario ya existe");
            }
        }
        Usuario usuario = new Usuario();
        usuario.setId(nextId++);
        usuario.setNombre(nombre);
        usuario.setContrasena(contrasena);
        usuario.setEmail(email);
        usuarios.add(usuario);
        return usuario;
    }

    public Usuario login(String nombre, String contrasena) throws Exception {
        for (Usuario u : usuarios) {
            if (u.getNombre().equals(nombre)) {
                if (u.getContrasena().equals(contrasena)) {
                    return u;
                } else {
                    throw new Exception("Contraseña incorrecta");
                }
            }
        }
        throw new Exception("El usuario no existe. Por favor cree una cuenta para poder ingresar.");
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
