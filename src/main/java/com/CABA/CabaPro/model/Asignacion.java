package com.CABA.CabaPro.model;

/**
 * Representa la asignación de un árbitro a un partido.
 * Incluye especialidad, estado y referencias a árbitro y partido.
 */
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
@Entity
public class Asignacion {
    /** Identificador único de la asignación. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Especialidad del árbitro en la asignación. */
    @Enumerated(EnumType.STRING)
    private EspecialidadEnum especialidad;

    /** Estado de la asignación (PENDIENTE, ACEPTADA, RECHAZADA). */
    @Enumerated(EnumType.STRING)
    private EstadoAsignacionEnum estado;

    /** Árbitro asignado al partido. */
    @ManyToOne
    @JoinColumn(name = "arbitro_correo", referencedColumnName = "correo")
    private Usuario arbitro;

    /** Partido al que se asigna el árbitro. */
    @ManyToOne
    @JoinColumn(name = "partido_id")
    private Partido partido;

    // Métodos del diagrama
    /** Marca la asignación como aceptada. */
    public void aceptar() {
        this.estado = EstadoAsignacionEnum.ACEPTADA;
    }

    /** Marca la asignación como rechazada. */
    public void rechazar() {
        this.estado = EstadoAsignacionEnum.RECHAZADA;
    }

    // Getters y setters
    /** Obtiene el ID de la asignación. */
    public Long getId() { return id; }
    /** Establece el ID de la asignación. */
    public void setId(Long id) { this.id = id; }

    /** Obtiene la especialidad de la asignación. */
    public EspecialidadEnum getEspecialidad() { return especialidad; }
    /** Establece la especialidad de la asignación. */
    public void setEspecialidad(EspecialidadEnum especialidad) { this.especialidad = especialidad; }

    /** Obtiene el estado de la asignación. */
    public EstadoAsignacionEnum getEstado() { return estado; }
    /** Establece el estado de la asignación. */
    public void setEstado(EstadoAsignacionEnum estado) { this.estado = estado; }

    /** Obtiene el árbitro asignado. */
    public Usuario getArbitro() { return arbitro; }
    /** Establece el árbitro asignado. */
    public void setArbitro(Usuario arbitro) { this.arbitro = arbitro; }

    /** Obtiene el partido asignado. */
    public Partido getPartido() { return partido; }
    /** Establece el partido asignado. */
    public void setPartido(Partido partido) { this.partido = partido; }
}
