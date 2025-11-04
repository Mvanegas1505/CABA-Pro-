package com.CABA.util.mapper;

import com.CABA.CabaPro.dto.api.torneo.TorneoResponse;
import com.CABA.CabaPro.model.Torneo;

/**
 * Mapeo de Torneo a TorneoResponse.
 */
public final class TorneoMapper {

    private TorneoMapper() {
    }

    public static TorneoResponse toResponse(Torneo t) {
        if (t == null)
            return null;
        TorneoResponse dto = new TorneoResponse();
        dto.setId(t.getId());
        dto.setNombre(t.getNombre());
        dto.setFechaInicio(t.getFechaInicio() != null ? t.getFechaInicio().toString() : null);
        dto.setFechaFin(t.getFechaFin() != null ? t.getFechaFin().toString() : null);
        dto.setPartidosCount(t.getPartidos() != null ? t.getPartidos().size() : 0);
        return dto;
    }
}
