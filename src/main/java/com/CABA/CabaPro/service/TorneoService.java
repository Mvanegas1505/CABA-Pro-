package com.CABA.CabaPro.service;

import com.CABA.CabaPro.model.Torneo;
import com.CABA.CabaPro.repository.TorneoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TorneoService {
    @Autowired
    private TorneoRepository torneoRepository;

    public List<Torneo> getAllTorneos() {
        return torneoRepository.findAll();
    }

    public Optional<Torneo> getTorneoById(Long id) {
        return torneoRepository.findById(id);
    }

    public Torneo saveTorneo(Torneo torneo) {
        return torneoRepository.save(torneo);
    }

    public void deleteTorneo(Long id) {
        torneoRepository.deleteById(id);
    }
}
