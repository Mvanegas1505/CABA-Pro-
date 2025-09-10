/**
 * Representa un usuario del sistema (árbitro o administrador).
 */
package com.CABA.CabaPro.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {

    /**
     * Correo electrónico único del usuario.
     */
    @Id
    @Column(unique = true, nullable = false)
    private String correo;

    /**
     * Nombre completo del usuario.
     */
    private String nombre;

    /**
     * Contraseña de acceso.
     */
    private String contrasena;

    /**
     * Rol del usuario (ÁRBITRO, ADMIN).
     */
    @Enumerated(EnumType.STRING)
    private RolEnum rol;

    // Solo para árbitros

    /**
     * Especialidad del árbitro (ejemplo: CAMPO, MESA).
     */
    private String especialidad; // Ej: "CAMPO", "MESA"
    /**
     * Escalafón del árbitro (ejemplo: FIBA, PRIMERA, SEGUNDA).
     */
    private String escalafon; // Ej: "FIBA", "PRIMERA", "SEGUNDA"
    /**
     * URL de la foto de perfil del usuario.
     */
    private String fotoPerfilUrl;

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return correo electrónico del usuario
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico del usuario.
     *
     * @param correo correo electrónico a asignar
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene el nombre completo del usuario.
     *
     * @return nombre completo del usuario
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre completo del usuario.
     *
     * @param nombre nombre completo a asignar
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return contraseña del usuario
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param contrasena contraseña a asignar
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * Obtiene el rol del usuario.
     *
     * @return rol del usuario
     */
    public RolEnum getRol() {
        return rol;
    }

    /**
     * Establece el rol del usuario.
     *
     * @param rol rol a asignar
     */
    public void setRol(RolEnum rol) {
        this.rol = rol;
    }

    /**
     * Obtiene la especialidad del árbitro.
     *
     * @return especialidad del árbitro
     */
    public String getEspecialidad() {
        return especialidad;
    }

    /**
     * Establece la especialidad del árbitro.
     *
     * @param especialidad especialidad a asignar
     */
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    /**
     * Obtiene el escalafón del árbitro.
     *
     * @return escalafón del árbitro
     */
    public String getEscalafon() {
        return escalafon;
    }

    /**
     * Establece el escalafón del árbitro.
     *
     * @param escalafon escalafón a asignar
     */
    public void setEscalafon(String escalafon) {
        this.escalafon = escalafon;
    }

    /**
     * Obtiene la URL de la foto de perfil.
     *
     * @return URL de la foto de perfil
     */
    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }

    /**
     * Establece la URL de la foto de perfil.
     *
     * @param fotoPerfilUrl URL de la foto de perfil a asignar
     */
    public void setFotoPerfilUrl(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
    }
}
