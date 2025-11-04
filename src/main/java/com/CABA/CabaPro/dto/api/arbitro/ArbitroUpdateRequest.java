package com.CABA.CabaPro.dto.api.arbitro;

import com.CABA.util.validation.OneOfEnum;
import com.CABA.CabaPro.model.EscalafonEnum;
import com.CABA.CabaPro.model.EspecialidadEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para actualizar datos de perfil del árbitro. El correo/rol no se
 * modifican aquí.
 */
@Schema(description = "Datos para actualizar el perfil del árbitro")
public class ArbitroUpdateRequest {

    @NotBlank
    @Size(min = 2, max = 100)
    @Schema(description = "Nombre completo", example = "Juan Pérez")
    private String nombre;

    @OneOfEnum(enumClass = EspecialidadEnum.class, ignoreCase = true, message = "Especialidad inválida")
    @Schema(description = "Especialidad válida según EspecialidadEnum", example = "PRINCIPAL")
    private String especialidad;

    @OneOfEnum(enumClass = EscalafonEnum.class, ignoreCase = true, message = "Escalafón inválido")
    @Schema(description = "Escalafón válido según EscalafonEnum", example = "PRIMERA")
    private String escalafon;

    @Schema(description = "URL pública de la foto de perfil")
    private String fotoPerfilUrl;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
