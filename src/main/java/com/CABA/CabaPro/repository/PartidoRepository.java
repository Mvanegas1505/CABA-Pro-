package com.CABA.CabaPro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CABA.CabaPro.model.Partido;

@Repository
public interface PartidoRepository extends JpaRepository<Partido, Long> {
    // Buscar partidos por torneo para permitir eliminación segura
    java.util.List<Partido> findByTorneoId(Long torneoId);
}
