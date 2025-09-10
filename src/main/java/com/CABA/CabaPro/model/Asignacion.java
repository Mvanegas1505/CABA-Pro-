package com.CABA.CabaPro.model;

import jakarta.persistence.*;
import com.CABA.CabaPro.model.EspecialidadEnum;
import com.CABA.CabaPro.model.EstadoAsignacionEnum;
import com.CABA.CabaPro.model.Usuario;
@Entity
public class Asignacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EspecialidadEnum especialidad;

    @Enumerated(EnumType.STRING)
    private EstadoAsignacionEnum estado;

    @ManyToOne
    @JoinColumn(name = "arbitro_correo", referencedColumnName = "correo")
    private Usuario arbitro;

    @ManyToOne
    @JoinColumn(name = "partido_id")
    private Partido partido;

    // Métodos del diagrama
    public void aceptar() {
        this.estado = EstadoAsignacionEnum.ACEPTADA;
    }

    public void rechazar() {
        this.estado = EstadoAsignacionEnum.RECHAZADA;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public EspecialidadEnum getEspecialidad() { return especialidad; }
    public void setEspecialidad(EspecialidadEnum especialidad) { this.especialidad = especialidad; }

    public EstadoAsignacionEnum getEstado() { return estado; }
    public void setEstado(EstadoAsignacionEnum estado) { this.estado = estado; }

    public Usuario getArbitro() { return arbitro; }
    public void setArbitro(Usuario arbitro) { this.arbitro = arbitro; }

    public Partido getPartido() { return partido; }
    public void setPartido(Partido partido) { this.partido = partido; }
}
