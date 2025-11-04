package com.CABA.util.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.CABA.CabaPro.dto.api.asignacion.AsignacionResponse;
import com.CABA.CabaPro.dto.api.liquidacion.LiquidacionDetailResponse;
import com.CABA.CabaPro.dto.api.liquidacion.LiquidacionSummaryResponse;
import com.CABA.CabaPro.model.Asignacion;
import com.CABA.CabaPro.model.Liquidacion;

/**
 * Mapeos para Liquidacion a DTOs de resumen y detalle.
 */
public final class LiquidacionMapper {

    private LiquidacionMapper() {
    }

    public static LiquidacionSummaryResponse toSummary(Liquidacion l) {
        if (l == null)
            return null;
        LiquidacionSummaryResponse dto = new LiquidacionSummaryResponse();
        dto.setId(l.getId());
        dto.setMes(l.getMes());
        dto.setAnio(l.getAnio());
        dto.setMontoTotal(l.getMontoTotal() != null ? l.getMontoTotal().toPlainString() : null);
        return dto;
    }

    public static LiquidacionDetailResponse toDetail(Liquidacion l) {
        if (l == null)
            return null;
        LiquidacionDetailResponse dto = new LiquidacionDetailResponse();
        dto.setId(l.getId());
        dto.setMes(l.getMes());
        dto.setAnio(l.getAnio());
        dto.setMontoTotal(l.getMontoTotal() != null ? l.getMontoTotal().toPlainString() : null);
        if (l.getUsuario() != null) {
            dto.setUsuarioCorreo(l.getUsuario().getCorreo());
            dto.setUsuarioNombre(l.getUsuario().getNombre());
        }
        List<Asignacion> partidas = l.getPartidas();
        if (partidas != null) {
            List<AsignacionResponse> asignaciones = partidas.stream()
                    .map(AsignacionMapper::toResponse)
                    .collect(Collectors.toList());
            dto.setPartidas(asignaciones);
        }
        return dto;
    }
}
