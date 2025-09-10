package com.CABA.CabaPro.service;

import com.CABA.CabaPro.model.Liquidacion;
import com.CABA.CabaPro.repository.LiquidacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LiquidacionService {
    @Autowired
    private LiquidacionRepository liquidacionRepository;

    public List<Liquidacion> getLiquidacionesPorArbitro(String correo) {
        return liquidacionRepository.findByUsuario_Correo(correo);
    }
    public List<Liquidacion> getAllLiquidaciones() {
        return liquidacionRepository.findAll();
    }

    public Optional<Liquidacion> getLiquidacionById(Long id) {
        return liquidacionRepository.findById(id);
    }

    public Liquidacion saveLiquidacion(Liquidacion liquidacion) {
        return liquidacionRepository.save(liquidacion);
    }

    public void deleteLiquidacion(Long id) {
        liquidacionRepository.deleteById(id);
    }
}
