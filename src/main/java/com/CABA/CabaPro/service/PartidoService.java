package com.CABA.CabaPro.service;

import com.CABA.CabaPro.model.Partido;
import com.CABA.CabaPro.repository.PartidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartidoService {
    @Autowired
    private PartidoRepository partidoRepository;

    public List<Partido> getAllPartidos() {
        return partidoRepository.findAll();
    }

    public Optional<Partido> getPartidoById(Long id) {
        return partidoRepository.findById(id);
    }

    public Partido savePartido(Partido partido) {
        return partidoRepository.save(partido);
    }

    public void deletePartido(Long id) {
        partidoRepository.deleteById(id);
    }
}
