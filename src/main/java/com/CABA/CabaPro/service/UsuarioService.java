package com.CABA.CabaPro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CABA.CabaPro.model.Usuario;
import com.CABA.CabaPro.repository.UsuarioRepository;

@Service
public class UsuarioService {
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
        // Devuelve todos los usuarios con rol ARBITRO
        public List<Usuario> getArbitros() {
            // Prefer repository query by role; fallback to filtering if method not available
            try {
                return usuarioRepository.findByRol(com.CABA.CabaPro.model.RolEnum.ARBITRO);
            } catch (Exception ex) {
                return usuarioRepository.findAll().stream()
                        .filter(u -> u.getRol() != null && u.getRol().name().equals("ARBITRO"))
                        .toList();
            }
        }

        // Devuelve un usuario por su ID (correo)
        public Optional<Usuario> getUsuarioById(String id) {
            return usuarioRepository.findById(id);
        }

    private final String ADMIN_PASSWORD = "ADMIN1";

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    public Usuario findByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo).orElse(null);
    }

    public Usuario getUsuarioByCorreo(String correo) {
        return findByCorreo(correo);
    }

    public boolean loginAdmin(String contrasena) {
        return ADMIN_PASSWORD.equals(contrasena);
    }

    public Usuario crearUsuario(String nombre, String contrasena, String correo) throws Exception {
        if (usuarioRepository.findByCorreo(correo).isPresent()) {
            throw new Exception("El correo ya está registrado");
        }

        // Validar que la contraseña no sea vacía o contenga solo espacios
        if (contrasena == null || contrasena.trim().isEmpty()) {
            throw new Exception("La contraseña no puede estar vacía ni contener solo espacios");
        }

        // Validación mínima de longitud
        if (contrasena.length() < 8) {
            throw new Exception("La contraseña debe tener al menos 8 caracteres");
        }

        // Política de complejidad: al menos una mayúscula, una minúscula y un carácter especial
        if (!contrasena.matches(".*[A-Z].*")) {
            throw new Exception("La contraseña debe contener al menos una letra mayúscula");
        }
        if (!contrasena.matches(".*[a-z].*")) {
            throw new Exception("La contraseña debe contener al menos una letra minúscula");
        }
        if (!contrasena.matches(".*[^A-Za-z0-9].*")) {
            throw new Exception("La contraseña debe contener al menos un carácter especial");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        // Encriptar la contraseña antes de guardar
        usuario.setContrasena(passwordEncoder.encode(contrasena));
        usuario.setCorreo(correo);
        usuario.setRol(com.CABA.CabaPro.model.RolEnum.ARBITRO);
        return usuarioRepository.save(usuario);
    }

    public Usuario login(String correo, String contrasena) throws Exception {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (passwordEncoder.matches(contrasena, usuario.getContrasena())) {
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
            if (!passwordEncoder.matches(contrasenaAntigua, usuario.getContrasena())) {
                throw new Exception("La contraseña antigua es incorrecta");
            }
            // Validar nueva contraseña según la misma política
            if (contrasenaNueva == null || contrasenaNueva.trim().isEmpty()) {
                throw new Exception("La contraseña nueva no puede estar vacía");
            }
            if (contrasenaNueva.length() < 8) {
                throw new Exception("La contraseña debe tener al menos 8 caracteres");
            }
            if (!contrasenaNueva.matches(".*[A-Z].*")) {
                throw new Exception("La contraseña debe contener al menos una letra mayúscula");
            }
            if (!contrasenaNueva.matches(".*[a-z].*")) {
                throw new Exception("La contraseña debe contener al menos una letra minúscula");
            }
            if (!contrasenaNueva.matches(".*[^A-Za-z0-9].*")) {
                throw new Exception("La contraseña debe contener al menos un carácter especial");
            }

            usuario.setContrasena(passwordEncoder.encode(contrasenaNueva));
            usuarioRepository.save(usuario);
            return true;
        }
        throw new Exception("Usuario no encontrado");
    }
}
