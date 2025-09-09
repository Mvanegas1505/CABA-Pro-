package com.CABA.CabaPro.repository;

import com.CABA.CabaPro.model.Liquidacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiquidacionRepository extends JpaRepository<Liquidacion, Long> {
    // Métodos personalizados si son necesarios
}
