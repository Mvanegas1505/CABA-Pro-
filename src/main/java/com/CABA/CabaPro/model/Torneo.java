package com.CABA.CabaPro.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

/**
 * Representa un torneo de baloncesto.
 * Incluye información básica como nombre, fechas y partidos asociados.
 */
@Entity
public class Torneo {

    /**
     * Identificador único del torneo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre oficial del torneo.
     */
    private String nombre;

    /**
     * Fecha en la que inicia el torneo.
     */
    private LocalDate fechaInicio;

    /**
     * Fecha en la que finaliza el torneo.
     */
    private LocalDate fechaFin;

    /**
     * Lista de partidos que forman parte del torneo.
     */
    @OneToMany(mappedBy = "torneo")
    private List<Partido> partidos;

    /**
     * Devuelve el identificador único del torneo.
     * @return id del torneo
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único del torneo.
     * @param id identificador a asignar
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Devuelve el nombre oficial del torneo.
     * @return nombre del torneo
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre oficial del torneo.
     * @param nombre nombre a asignar
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve la fecha de inicio del torneo.
     * @return fecha de inicio
     */
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Establece la fecha de inicio del torneo.
     * @param fechaInicio fecha a asignar
     */
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Devuelve la fecha de finalización del torneo.
     * @return fecha de finalización
     */
    public LocalDate getFechaFin() {
        return fechaFin;
    }

    /**
     * Establece la fecha de finalización del torneo.
     * @param fechaFin fecha a asignar
     */
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * Devuelve la lista de partidos asociados al torneo.
     * @return lista de partidos
     */
    public List<Partido> getPartidos() {
        return partidos;
    }

    /**
     * Establece la lista de partidos del torneo.
     * @param partidos lista de partidos a asignar
     */
    public void setPartidos(List<Partido> partidos) {
        this.partidos = partidos;
    }

    /**
     * Devuelve la lista de partidos del torneo.
     * @return lista de partidos
     */
    public List<Partido> obtenerPartidos() {
        return partidos;
    }

    /**
     * Define la tarifa para un escalafón específico.
     * @param escalafon escalafón de árbitro
     * @param monto monto de la tarifa
     */
    public void definirTarifa(EscalafonEnum escalafon, double monto) {
        // Implementar lógica para definir tarifa
    }

    /**
     * Filtra los partidos por fecha y árbitro.
     * @param fechaInicio fecha inicial
     * @param fechaFin fecha final
     * @param arbitro árbitro a filtrar
     * @return lista filtrada de partidos
     */
    public List<Partido> filtrarPartidos(LocalDate fechaInicio, LocalDate fechaFin, Usuario arbitro) {
        // Implementar lógica de filtrado
        return null;
    }

    /**
     * Obtiene la tarifa según el escalafón.
     * @param escalafon nombre del escalafón
     * @return tarifa correspondiente
     */
    public java.math.BigDecimal getTarifaPorEscalafon(String escalafon) {
        if (escalafon == null)
            return java.math.BigDecimal.ZERO;
        switch (escalafon.toUpperCase()) {
            case "FIBA":
                return new java.math.BigDecimal("10000");
            case "PRIMERA":
                return new java.math.BigDecimal("7000");
            case "SEGUNDA":
                return new java.math.BigDecimal("5000");
            default:
                return java.math.BigDecimal.ZERO;
        }
    }
}