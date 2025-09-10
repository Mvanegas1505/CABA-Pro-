    
package com.CABA.CabaPro.repository;

import com.CABA.CabaPro.model.Asignacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsignacionRepository extends JpaRepository<Asignacion, Long> {
    // Buscar asignaciones por correo de árbitro y estado
    List<Asignacion> findByArbitroCorreoAndEstado(String correo, com.CABA.CabaPro.model.EstadoAsignacionEnum estado);
    // Métodos personalizados si son necesarios
    
}
