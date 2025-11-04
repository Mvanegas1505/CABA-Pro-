package com.CABA.CabaPro.dto.api.torneo;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resumen de torneo")
public class TorneoResponse {

    @Schema(description = "ID del torneo")
    private Long id;

    @Schema(description = "Nombre del torneo")
    private String nombre;

    @Schema(description = "Fecha de inicio (YYYY-MM-DD)")
    private String fechaInicio;

    @Schema(description = "Fecha de fin (YYYY-MM-DD)")
    private String fechaFin;

    @Schema(description = "Cantidad de partidos asociados")
    private int partidosCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getPartidosCount() {
        return partidosCount;
    }

    public void setPartidosCount(int partidosCount) {
        this.partidosCount = partidosCount;
    }
}
