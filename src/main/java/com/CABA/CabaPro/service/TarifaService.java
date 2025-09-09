package com.CABA.CabaPro.service;

import com.CABA.CabaPro.model.EscalafonEnum;
import com.CABA.CabaPro.model.Tarifa;
import com.CABA.CabaPro.model.Torneo;
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

    // 🔥 Método extra para simplificar lógica de controladores
    public Tarifa crearTarifa(Torneo torneo, EscalafonEnum escalafon, Double monto) {
        Tarifa tarifa = new Tarifa();
        tarifa.setTorneo(torneo);
        tarifa.setEscalafon(escalafon);
        tarifa.setMonto(monto);
        return tarifaRepository.save(tarifa);
    }
}
