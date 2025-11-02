package com.CABA.CabaPro.repository;

import com.CABA.CabaPro.model.Liquidacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiquidacionRepository extends JpaRepository<Liquidacion, Long> {
    java.util.List<Liquidacion> findByUsuario_Correo(String correo);

    java.util.Optional<Liquidacion> findTopByUsuario_CorreoOrderByIdDesc(String correo);
}
