package com.CABA.CabaPro.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CABA.CabaPro.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Optional<Usuario> findByCorreo(String correo);
    java.util.List<Usuario> findByRol(com.CABA.CabaPro.model.RolEnum rol);
}