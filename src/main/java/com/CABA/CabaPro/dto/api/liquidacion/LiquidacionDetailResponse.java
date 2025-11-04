package com.CABA.CabaPro.dto.api.liquidacion;

import java.util.List;

import com.CABA.CabaPro.dto.api.asignacion.AsignacionResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Detalle de liquidación con partidas/asignaciones")
public class LiquidacionDetailResponse extends LiquidacionSummaryResponse {

    @Schema(description = "Correo del árbitro dueño de la liquidación")
    private String usuarioCorreo;

    @Schema(description = "Nombre del árbitro")
    private String usuarioNombre;

    @Schema(description = "Asignaciones incluidas en la liquidación")
    private List<AsignacionResponse> partidas;

    public String getUsuarioCorreo() {
        return usuarioCorreo;
    }

    public void setUsuarioCorreo(String usuarioCorreo) {
        this.usuarioCorreo = usuarioCorreo;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public List<AsignacionResponse> getPartidas() {
        return partidas;
    }

    public void setPartidas(List<AsignacionResponse> partidas) {
        this.partidas = partidas;
    }
}
