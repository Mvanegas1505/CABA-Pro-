package com.CABA.util.mapper;

import java.time.format.DateTimeFormatter;

import com.CABA.CabaPro.dto.api.partido.PartidoResponse;
import com.CABA.CabaPro.model.Partido;

/**
 * Mapeo de Partido a PartidoResponse.
 */
public final class PartidoMapper {

    private static final DateTimeFormatter ISO_DATE = DateTimeFormatter.ISO_DATE;

    private PartidoMapper() {
    }

    public static PartidoResponse toResponse(Partido p) {
        if (p == null)
            return null;
        PartidoResponse dto = new PartidoResponse();
        dto.setId(p.getId());
        if (p.getFecha() != null)
            dto.setFecha(p.getFecha().format(ISO_DATE));
        dto.setHora(p.getHora());
        dto.setLugar(p.getLugar());
        if (p.getTorneo() != null) {
            dto.setTorneoId(p.getTorneo().getId());
            dto.setTorneoNombre(p.getTorneo().getNombre());
        }
        dto.setAsignacionesCount(p.getAsignaciones() != null ? p.getAsignaciones().size() : 0);
        return dto;
    }
}
