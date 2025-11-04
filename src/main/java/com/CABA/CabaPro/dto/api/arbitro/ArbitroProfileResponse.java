package com.CABA.CabaPro.dto.api.arbitro;

import com.CABA.CabaPro.model.RolEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO de solo lectura con la información pública y de perfil del
 * árbitro/usuario actual.
 * No expone contraseña ni campos sensibles.
 */
@Schema(description = "Perfil del árbitro (usuario) autenticado")
public class ArbitroProfileResponse {

    @Schema(description = "Correo único (ID del usuario)")
    private String correo;

    @Schema(description = "Nombre completo del usuario")
    private String nombre;

    @Schema(description = "Rol del usuario", example = "ARBITRO")
    private RolEnum rol;

    @Schema(description = "Especialidad (texto consistente con EspecialidadEnum)")
    private String especialidad;

    @Schema(description = "Escalafón (texto consistente con EscalafonEnum)")
    private String escalafon;

    @Schema(description = "URL de la foto de perfil")
    private String fotoPerfilUrl;

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public RolEnum getRol() {
        return rol;
    }

    public void setRol(RolEnum rol) {
        this.rol = rol;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getEscalafon() {
        return escalafon;
    }

    public void setEscalafon(String escalafon) {
        this.escalafon = escalafon;
    }

    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }

    public void setFotoPerfilUrl(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
    }
}
