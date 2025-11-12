package com.CABA.CabaPro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.CABA.CabaPro.model.Partido;
import com.CABA.CabaPro.model.Tarifa;
import com.CABA.CabaPro.model.Torneo;
import com.CABA.CabaPro.repository.PartidoRepository;
import com.CABA.CabaPro.repository.TarifaRepository;
import com.CABA.CabaPro.repository.TorneoRepository;

@Service
public class TorneoService {
    @Autowired
    private TorneoRepository torneoRepository;

    @Autowired
    private TarifaRepository tarifaRepository;

    @Autowired
    private PartidoRepository partidoRepository;

    public List<Torneo> getAllTorneos() {
        return torneoRepository.findAll();
    }

    public Optional<Torneo> getTorneoById(Long id) {
        return torneoRepository.findById(id);
    }

    public Torneo saveTorneo(Torneo torneo) {
        return torneoRepository.save(torneo);
    }

    /**
     * Delete a tournament and its dependent entities (tarifas, partidos) safely.
     * Runs in a transaction so either everything is removed or nothing.
     */
    @Transactional
    public void deleteTorneo(Long id) {
        // Remove tarifas linked to torneo
        List<Tarifa> tarifas = tarifaRepository.findByTorneoId(id);
        if (tarifas != null && !tarifas.isEmpty()) {
            tarifaRepository.deleteAll(tarifas);
        }

        // Remove partidos linked to torneo
        List<Partido> partidos = partidoRepository.findByTorneoId(id);
        if (partidos != null && !partidos.isEmpty()) {
            partidoRepository.deleteAll(partidos);
        }

        // Finally remove the torneo
        torneoRepository.deleteById(id);
    }
}
