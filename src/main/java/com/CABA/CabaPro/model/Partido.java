package com.CABA.CabaPro.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import com.CABA.CabaPro.model.Torneo;
import com.CABA.CabaPro.model.Tarifa;
import com.CABA.CabaPro.model.Usuario;
import com.CABA.CabaPro.model.EspecialidadEnum;

@Entity
public class Partido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;

    private String hora;
    private String lugar;

    @ManyToOne
    @JoinColumn(name = "torneo_id")
    private Torneo torneo;

    @ManyToOne
    @JoinColumn(name = "tarifa_id")
    private Tarifa tarifa;

    // Métodos del diagrama
    public void asignarArbitro(Usuario arbitro, EspecialidadEnum rol) {
        // Implementar lógica para asignar árbitro
    }

    public java.util.List<Asignacion> obtenerAsignaciones() {
        // Implementar lógica para obtener asignaciones
        return null;
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

    public Tarifa getTarifa() {
        return tarifa;
    }

    public void setTarifa(Tarifa tarifa) {
        this.tarifa = tarifa;
    }
}
