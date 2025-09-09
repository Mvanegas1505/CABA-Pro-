package com.CABA.CabaPro.service;

import com.CABA.CabaPro.model.Tarifa;
import com.CABA.CabaPro.repository.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarifaService {
    @Autowired
    private TarifaRepository tarifaRepository;

    public List<Tarifa> getAllTarifas() {
        return tarifaRepository.findAll();
    }

    public Optional<Tarifa> getTarifaById(Long id) {
        return tarifaRepository.findById(id);
    }

    public Tarifa saveTarifa(Tarifa tarifa) {
        return tarifaRepository.save(tarifa);
    }

    public void deleteTarifa(Long id) {
        tarifaRepository.deleteById(id);
    }
}
