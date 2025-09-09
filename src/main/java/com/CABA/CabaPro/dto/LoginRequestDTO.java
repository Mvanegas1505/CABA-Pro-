package com.CABA.CabaPro.dto;

/**
 * DTO para el login de usuario (admin o arbitro).
 * Cumple con el estilo de Google Java Style Guide.
 */
public class LoginRequestDTO {
    private String correo;
    private String contrasena;

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
