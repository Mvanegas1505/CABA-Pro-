package com.CABA.CabaPro.dto.api.partido;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Partido con resumen de torneo y asignaciones")
public class PartidoResponse {

    @Schema(description = "ID del partido")
    private Long id;

    @Schema(description = "Fecha (YYYY-MM-DD)")
    private String fecha;

    @Schema(description = "Hora (texto libre)")
    private String hora;

    @Schema(description = "Lugar del partido")
    private String lugar;

    @Schema(description = "ID del torneo")
    private Long torneoId;

    @Schema(description = "Nombre del torneo")
    private String torneoNombre;

    @Schema(description = "Cantidad de asignaciones (árbitros) en el partido")
    private int asignacionesCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Long getTorneoId() {
        return torneoId;
    }

    public void setTorneoId(Long torneoId) {
        this.torneoId = torneoId;
    }

    public String getTorneoNombre() {
        return torneoNombre;
    }

    public void setTorneoNombre(String torneoNombre) {
        this.torneoNombre = torneoNombre;
    }

    public int getAsignacionesCount() {
        return asignacionesCount;
    }

    public void setAsignacionesCount(int asignacionesCount) {
        this.asignacionesCount = asignacionesCount;
    }
}
