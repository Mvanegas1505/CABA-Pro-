package com.CABA.util.mapper;

import com.CABA.CabaPro.dto.api.arbitro.ArbitroProfileResponse;
import com.CABA.CabaPro.dto.api.arbitro.ArbitroUpdateRequest;
import com.CABA.CabaPro.model.Usuario;

/**
 * Conversión entre Usuario (entidad) y DTOs de árbitro.
 */
public final class UsuarioMapper {

    private UsuarioMapper() {
    }

    /**
     * Mapea un Usuario a un DTO de perfil de árbitro.
     */
    public static ArbitroProfileResponse toProfile(Usuario u) {
        if (u == null)
            return null;
        ArbitroProfileResponse dto = new ArbitroProfileResponse();
        dto.setCorreo(u.getCorreo());
        dto.setNombre(u.getNombre());
        dto.setRol(u.getRol());
        dto.setEspecialidad(u.getEspecialidad());
        dto.setEscalafon(u.getEscalafon());
        dto.setFotoPerfilUrl(u.getFotoPerfilUrl());
        return dto;
    }

    /**
     * Aplica campos de actualización del perfil sobre la entidad (no persiste).
     */
    public static void applyUpdate(Usuario u, ArbitroUpdateRequest req) {
        if (u == null || req == null)
            return;
        if (req.getNombre() != null)
            u.setNombre(req.getNombre());
        if (req.getEspecialidad() != null)
            u.setEspecialidad(req.getEspecialidad());
        if (req.getEscalafon() != null)
            u.setEscalafon(req.getEscalafon());
        if (req.getFotoPerfilUrl() != null)
            u.setFotoPerfilUrl(req.getFotoPerfilUrl());
    }
}
