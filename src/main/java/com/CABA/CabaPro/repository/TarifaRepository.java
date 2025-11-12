package com.CABA.CabaPro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CABA.CabaPro.model.Tarifa;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, Long> {
    // Buscar tarifas por torneo para permitir eliminación segura
    java.util.List<Tarifa> findByTorneoId(Long torneoId);
}
