package com.CABA.CabaPro.repository;

import com.CABA.CabaPro.model.Asignacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsignacionRepository extends JpaRepository<Asignacion, Long> {
    // Métodos personalizados si son necesarios
}
