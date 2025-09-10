package com.CABA.CabaPro.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import com.CABA.CabaPro.model.EscalafonEnum;
import com.CABA.CabaPro.model.Partido;
import com.CABA.CabaPro.model.Usuario;

@Entity
public class Torneo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    // Relación con Partido (asumiendo que existe la clase Partido)
    @OneToMany(mappedBy = "torneo")
    private List<Partido> partidos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<Partido> getPartidos() {
        return partidos;
    }

    public void setPartidos(List<Partido> partidos) {
        this.partidos = partidos;
    }

    // Métodos del diagrama
    public List<Partido> obtenerPartidos() {
        return partidos;
    }

    public void definirTarifa(EscalafonEnum escalafon, double monto) {
        // Implementar lógica para definir tarifa
    }

    public List<Partido> filtrarPartidos(LocalDate fechaInicio, LocalDate fechaFin, Usuario arbitro) {
        // Implementar lógica de filtrado
        return null;
    }
    
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
