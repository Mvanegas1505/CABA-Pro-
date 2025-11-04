package com.CABA.CabaPro.dto.api.liquidacion;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resumen de liquidación")
public class LiquidacionSummaryResponse {

    @Schema(description = "ID de la liquidación")
    private Long id;

    @Schema(description = "Mes de la liquidación", example = "7")
    private int mes;

    @Schema(description = "Año de la liquidación", example = "2025")
    private int anio;

    @Schema(description = "Monto total")
    private String montoTotal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(String montoTotal) {
        this.montoTotal = montoTotal;
    }
}
