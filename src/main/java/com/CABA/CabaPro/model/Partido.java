package com.CABA.CabaPro.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import com.CABA.CabaPro.model.Torneo;
// import de Tarifa eliminado
import com.CABA.CabaPro.model.Usuario;
// imports no usados eliminados
// import com.CABA.CabaPro.model.Torneo;
// import com.CABA.CabaPro.model.Usuario;
// import com.CABA.CabaPro.model.EspecialidadEnum;
@Entity
public class Partido {
    @OneToMany(mappedBy = "partido", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<Asignacion> asignaciones = new java.util.ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;

    private String hora;
    private String lugar;

    @ManyToOne
    @JoinColumn(name = "torneo_id")
    private Torneo torneo;

    // ...tarifa eliminado...

    // Métodos del diagrama
    public void asignarArbitro(Usuario arbitro, EspecialidadEnum rol) {
        // Implementar lógica para asignar árbitro
    }

    // Getter y setter para asignaciones
    public java.util.List<Asignacion> getAsignaciones() {
        return asignaciones;
    }
    public void setAsignaciones(java.util.List<Asignacion> asignaciones) {
        this.asignaciones = asignaciones;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Torneo getTorneo() {
        return torneo;
    }

    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }

    // ...getTarifa y setTarifa eliminados...
}
