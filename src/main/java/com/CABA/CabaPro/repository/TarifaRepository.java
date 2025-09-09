package com.CABA.CabaPro.repository;

import com.CABA.CabaPro.model.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, Long> {
    // Métodos personalizados si son necesarios
}
