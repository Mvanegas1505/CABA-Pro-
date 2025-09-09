package com.CABA.CabaPro.model;

import jakarta.persistence.*;

@Entity
public class Tarifa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EscalafonEnum escalafon;

    @ManyToOne
    @JoinColumn(name = "torneo_id")
    private Torneo torneo;

    private double monto;

    // Métodos del diagrama
    public double calcularPago(int cantidad) {
        return monto * cantidad;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public EscalafonEnum getEscalafon() { return escalafon; }
    public void setEscalafon(EscalafonEnum escalafon) { this.escalafon = escalafon; }

    public Torneo getTorneo() { return torneo; }
    public void setTorneo(Torneo torneo) { this.torneo = torneo; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }
}
