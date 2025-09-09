package com.CABA.CabaPro.repository;

import com.CABA.CabaPro.model.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartidoRepository extends JpaRepository<Partido, Long> {
    // Métodos personalizados si son necesarios
}
