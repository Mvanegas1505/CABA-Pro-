package com.CABA.CabaPro.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CABA.CabaPro.model.Usuario;
import com.CABA.CabaPro.repository.UsuarioRepository;

@Service
public class UsuarioService {
    private final String ADMIN_PASSWORD = "ADMIN1";

    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean loginAdmin(String contrasena) {
        return ADMIN_PASSWORD.equals(contrasena);
    }

    public Usuario crearUsuario(String nombre, String contrasena, String correo) throws Exception {
        if (usuarioRepository.findByCorreo(correo).isPresent()) {
            throw new Exception("El correo ya está registrado");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setContrasena(contrasena);
        usuario.setCorreo(correo);
        return usuarioRepository.save(usuario);
    }

    public Usuario login(String correo, String contrasena) throws Exception {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (usuario.getContrasena().equals(contrasena)) {
                return usuario;
            } else {
                throw new Exception("Contraseña incorrecta");
            }
        }
        throw new Exception("El usuario no existe. Por favor cree una cuenta para poder ingresar.");
    }

    public boolean cambiarContrasena(String correo, String contrasenaAntigua, String contrasenaNueva) throws Exception {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (!usuario.getContrasena().equals(contrasenaAntigua)) {
                throw new Exception("La contraseña antigua es incorrecta");
            }
            usuario.setContrasena(contrasenaNueva);
            usuarioRepository.save(usuario);
            return true;
        }
        throw new Exception("Usuario no encontrado");
    }
}
