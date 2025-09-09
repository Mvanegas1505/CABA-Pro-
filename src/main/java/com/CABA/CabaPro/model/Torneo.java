package com.CABA.CabaPro.model;

import jakarta.persistence.*;
import java.util.Date;
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

    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    private Date fechaFin;

    // Relación con Partido (asumiendo que existe la clase Partido)
    @OneToMany(mappedBy = "torneo")
    private List<Partido> partidos;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }

    public List<Partido> getPartidos() { return partidos; }
    public void setPartidos(List<Partido> partidos) { this.partidos = partidos; }

    // Métodos del diagrama
    public List<Partido> obtenerPartidos() {
        return partidos;
    }

    public void definirTarifa(EscalafonEnum escalafon, double monto) {
        // Implementar lógica para definir tarifa
    }

    public List<Partido> filtrarPartidos(Date fechaInicio, Date fechaFin, Usuario arbitro) {
        // Implementar lógica de filtrado
        return null;
    }
}
