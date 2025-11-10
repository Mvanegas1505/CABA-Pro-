package com.CABA.CabaPro.model;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
/**
 * Representa un partido de baloncesto dentro de un torneo.
 * Incluye fecha, hora, lugar, torneo y asignaciones de árbitros.
 */
public class Partido {

    /**
     * Lista de asignaciones de árbitros para el partido.
     */
    @OneToMany(mappedBy = "partido", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<Asignacion> asignaciones = new java.util.ArrayList<>();

    /**
     * Identificador único del partido.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Fecha del partido.
     */
    private LocalDate fecha;

    /**
     * Hora del partido.
     */
    private String hora;

    /**
     * Lugar donde se juega el partido.
     */
    private String lugar;

    /**
     * Latitud de la ubicación (opcional, calculada por geocoding)
     */
    private Double latitude;

    /**
     * Longitud de la ubicación (opcional, calculada por geocoding)
     */
    private Double longitude;

    /**
     * Torneo al que pertenece el partido.
     */
    @ManyToOne
    @JoinColumn(name = "torneo_id")
    private Torneo torneo;

    /**
     * Asigna un árbitro al partido (lógica a implementar).
     * @param arbitro Árbitro a asignar.
     * @param rol Rol del árbitro en el partido.
     */
    public void asignarArbitro(Usuario arbitro, EspecialidadEnum rol) {
        // Implementar lógica para asignar árbitro
    }

    /**
     * Obtiene la lista de asignaciones de árbitros.
     * @return Lista de asignaciones.
     */
    public java.util.List<Asignacion> getAsignaciones() {
        return asignaciones;
    }

    /**
     * Establece la lista de asignaciones de árbitros.
     * @param asignaciones Lista de asignaciones a establecer.
     */
    public void setAsignaciones(java.util.List<Asignacion> asignaciones) {
        this.asignaciones = asignaciones;
    }

    /**
     * Obtiene el ID del partido.
     * @return ID del partido.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el ID del partido.
     * @param id ID a establecer.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene la fecha del partido.
     * @return Fecha del partido.
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha del partido.
     * @param fecha Fecha a establecer.
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene la hora del partido.
     * @return Hora del partido.
     */
    public String getHora() {
        return hora;
    }

    /**
     * Establece la hora del partido.
     * @param hora Hora a establecer.
     */
    public void setHora(String hora) {
        this.hora = hora;
    }

    /**
     * Obtiene el lugar del partido.
     * @return Lugar del partido.
     */
    public String getLugar() {
        return lugar;
    }

    /**
     * Establece el lugar del partido.
     * @param lugar Lugar a establecer.
     */
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * Obtiene el torneo al que pertenece el partido.
     * @return Torneo del partido.
     */
    public Torneo getTorneo() {
        return torneo;
    }

    /**
     * Establece el torneo al que pertenece el partido.
     * @param torneo Torneo a establecer.
     */
    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }
}
