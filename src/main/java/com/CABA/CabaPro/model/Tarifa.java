package com.CABA.CabaPro.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Representa una tarifa asociada a un torneo y un escalafón.
 */
@Entity
public class Tarifa {

    /**
     * Identificador único de la tarifa.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Escalafón al que pertenece la tarifa.
     */
    @Enumerated(EnumType.STRING)
    private EscalafonEnum escalafon;

    /**
     * Torneo asociado a la tarifa.
     */
    @ManyToOne
    @JoinColumn(name = "torneo_id")
    private Torneo torneo;

    /**
     * Monto de la tarifa.
     */
    private double monto;

    /**
     * Calcula el pago total basado en la cantidad proporcionada.
     * @param cantidad número de unidades
     * @return pago total calculado
     */
    public double calcularPago(int cantidad) {
        return monto * cantidad;
    }

    /**
     * Obtiene el identificador único de la tarifa.
     * @return id de la tarifa
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único de la tarifa.
     * @param id identificador a asignar
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el escalafón asociado a la tarifa.
     * @return escalafón de la tarifa
     */
    public EscalafonEnum getEscalafon() {
        return escalafon;
    }

    /**
     * Establece el escalafón asociado a la tarifa.
     * @param escalafon escalafón a asignar
     */
    public void setEscalafon(EscalafonEnum escalafon) {
        this.escalafon = escalafon;
    }

    /**
     * Obtiene el torneo asociado a la tarifa.
     * @return torneo asociado
     */
    public Torneo getTorneo() {
        return torneo;
    }

    /**
     * Establece el torneo asociado a la tarifa.
     * @param torneo torneo a asignar
     */
    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }

    /**
     * Obtiene el monto de la tarifa.
     * @return monto de la tarifa
     */
    public double getMonto() {
        return monto;
    }

    /**
     * Establece el monto de la tarifa.
     * @param monto monto a asignar
     */
    public void setMonto(double monto) {
        this.monto = monto;
    }
}
