package com.CABA.util.mapper;

import java.time.format.DateTimeFormatter;

import com.CABA.CabaPro.dto.api.asignacion.AsignacionResponse;
import com.CABA.CabaPro.model.Asignacion;

/**
 * Conversión entre Asignacion y su DTO de respuesta.
 */
public final class AsignacionMapper {

    private static final DateTimeFormatter ISO_DATE = DateTimeFormatter.ISO_DATE;

    private AsignacionMapper() {
    }

    public static AsignacionResponse toResponse(Asignacion a) {
        if (a == null)
            return null;
        AsignacionResponse dto = new AsignacionResponse();
        dto.setId(a.getId());
        dto.setEspecialidad(a.getEspecialidad() != null ? a.getEspecialidad().name() : null);
        dto.setEstado(a.getEstado() != null ? a.getEstado().name() : null);
        if (a.getPartido() != null) {
            dto.setPartidoId(a.getPartido().getId());
            if (a.getPartido().getFecha() != null) {
                dto.setPartidoFecha(a.getPartido().getFecha().format(ISO_DATE));
            }
            dto.setPartidoLugar(a.getPartido().getLugar());
            if (a.getPartido().getTorneo() != null) {
                dto.setTorneoNombre(a.getPartido().getTorneo().getNombre());
            }
        }
        return dto;
    }
}
