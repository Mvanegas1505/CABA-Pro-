package com.CABA.CabaPro.service;

import com.CABA.CabaPro.model.Asignacion;
import com.CABA.CabaPro.model.EstadoAsignacionEnum;
import com.CABA.CabaPro.model.Usuario;
import com.CABA.CabaPro.repository.AsignacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AsignacionService {
    public List<Asignacion> getAsignacionesPendientesPorArbitro(String correo) {
        return asignacionRepository.findByArbitroCorreoAndEstado(correo, EstadoAsignacionEnum.PENDIENTE);
    }

    public List<Asignacion> getAsignacionesAceptadasPorArbitro(String correo) {
        return asignacionRepository.findByArbitroCorreoAndEstado(correo, EstadoAsignacionEnum.ACEPTADA);
    }

    @Autowired
    private AsignacionRepository asignacionRepository;

    public List<Asignacion> getAllAsignaciones() {
        return asignacionRepository.findAll();
    }

    public Optional<Asignacion> getAsignacionById(Long id) {
        return asignacionRepository.findById(id);
    }

    public Asignacion saveAsignacion(Asignacion asignacion) {
        return asignacionRepository.save(asignacion);
    }

    public void deleteAsignacion(Long id) {
        asignacionRepository.deleteById(id);
    }

    // 🔥 Métodos especializados
    public Optional<Asignacion> aceptarAsignacion(Long id) {
        return actualizarEstadoAsignacion(id, EstadoAsignacionEnum.ACEPTADA);
    }

    public Optional<Asignacion> rechazarAsignacion(Long id) {
        return actualizarEstadoAsignacion(id, EstadoAsignacionEnum.RECHAZADA);
    }

    private Optional<Asignacion> actualizarEstadoAsignacion(Long id, EstadoAsignacionEnum nuevoEstado) {
        Optional<Asignacion> asignacionOpt = asignacionRepository.findById(id);
        if (asignacionOpt.isPresent()) {
            Asignacion asignacion = asignacionOpt.get();
            asignacion.setEstado(nuevoEstado);
            asignacionRepository.save(asignacion);
        }
        return asignacionOpt;
    }

    public List<Asignacion> findByUsuarioAndEstadoAndMesAnio(Usuario arbitro, EstadoAsignacionEnum estado, int mes,
            int anio) {
        // Filtrar por correo, estado y mes/año del partido
        List<Asignacion> todas = asignacionRepository.findByArbitroCorreoAndEstado(arbitro.getCorreo(), estado);
        java.util.List<Asignacion> filtradas = new java.util.ArrayList<>();
        for (Asignacion a : todas) {
            if (a.getPartido() != null && a.getPartido().getFecha() != null) {
                java.time.LocalDate fecha = a.getPartido().getFecha();
                if (fecha.getMonthValue() == mes && fecha.getYear() == anio) {
                    filtradas.add(a);
                }
            }
        }
        return filtradas;
    }
}
