package com.CABA.CabaPro.service;

import com.CABA.CabaPro.model.Partido;
import com.CABA.CabaPro.repository.PartidoRepository;
import com.CABA.CabaPro.service.location.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartidoService {
    @Autowired
    private PartidoRepository partidoRepository;

    @Autowired
    private LocationService locationService;

    public List<Partido> getAllPartidos() {
        return partidoRepository.findAll();
    }

    public Optional<Partido> getPartidoById(Long id) {
        return partidoRepository.findById(id);
    }

    public Partido savePartido(Partido partido) {
        // Si no tiene coordenadas, intentar resolver por la dirección (lugar)
        try {
            if ((partido.getLatitude() == null || partido.getLongitude() == null) && partido.getLugar() != null && !partido.getLugar().isBlank()) {
                locationService.geocode(partido.getLugar()).ifPresent(coords -> {
                    partido.setLatitude(coords.getLatitude());
                    partido.setLongitude(coords.getLongitude());
                });
            }
        } catch (Exception e) {
            // no interrumpir el guardado por fallos en geocoding
            System.err.println("Error resolving coordinates: " + e.getMessage());
        }
        return partidoRepository.save(partido);
    }

    public void deletePartido(Long id) {
        partidoRepository.deleteById(id);
    }
}
