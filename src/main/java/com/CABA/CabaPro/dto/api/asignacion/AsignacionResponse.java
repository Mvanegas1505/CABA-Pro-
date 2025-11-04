package com.CABA.CabaPro.dto.api.asignacion;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Asignación de un árbitro a un partido")
public class AsignacionResponse {

    @Schema(description = "ID de la asignación", example = "12")
    private Long id;

    @Schema(description = "Especialidad de la asignación", example = "PRINCIPAL")
    private String especialidad;

    @Schema(description = "Estado de la asignación", example = "PENDIENTE")
    private String estado;

    @Schema(description = "ID del partido asignado", example = "5")
    private Long partidoId;

    @Schema(description = "Fecha del partido (YYYY-MM-DD)")
    private String partidoFecha;

    @Schema(description = "Lugar del partido")
    private String partidoLugar;

    @Schema(description = "Nombre del torneo")
    private String torneoNombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getPartidoId() {
        return partidoId;
    }

    public void setPartidoId(Long partidoId) {
        this.partidoId = partidoId;
    }

    public String getPartidoFecha() {
        return partidoFecha;
    }

    public void setPartidoFecha(String partidoFecha) {
        this.partidoFecha = partidoFecha;
    }

    public String getPartidoLugar() {
        return partidoLugar;
    }

    public void setPartidoLugar(String partidoLugar) {
        this.partidoLugar = partidoLugar;
    }

    public String getTorneoNombre() {
        return torneoNombre;
    }

    public void setTorneoNombre(String torneoNombre) {
        this.torneoNombre = torneoNombre;
    }
}
