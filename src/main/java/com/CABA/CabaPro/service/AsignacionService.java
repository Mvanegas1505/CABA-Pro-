package com.CABA.CabaPro.service;

import com.CABA.CabaPro.model.Asignacion;
import com.CABA.CabaPro.repository.AsignacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AsignacionService {
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
}
