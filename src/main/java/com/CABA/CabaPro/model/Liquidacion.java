package com.CABA.CabaPro.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

/**
 * Representa una liquidación asociada a un usuario y sus asignaciones.
 * Incluye información sobre el mes, año, partidas y el monto total.
 */
@Entity
public class Liquidacion {

    /**
     * Identificador único de la liquidación.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Usuario asociado a la liquidación.
     */
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    /**
     * Mes de la liquidación.
     */
    private int mes;

    /**
     * Año de la liquidación.
     */
    private int anio;

    /**
     * Lista de partidas asociadas a la liquidación.
     */
    @OneToMany
    @JoinColumn(name = "liquidacion_id")
    private List<Asignacion> partidas;

    /**
     * Monto total de la liquidación.
     */
    private java.math.BigDecimal montoTotal;

    /**
     * Calcula el monto total de la liquidación sumando las asignaciones.
     * @return Monto total calculado.
     */
    public double calcularTotal() {
        double total = 0;
        if (partidas != null) {
            for (Asignacion asignacion : partidas) {
                // Aquí deberías obtener el monto de la tarifa asociada a la asignación
                // total += asignacion.getTarifa().getMonto();
            }
        }
        return total;
    }

    /**
     * Genera un archivo PDF con los detalles de la liquidación.
     */
    public void generarPDF() {
        // Implementar lógica para generar PDF
    }

    /**
     * Obtiene el monto total de la liquidación.
     * @return Monto total.
     */
    public java.math.BigDecimal getMontoTotal() {
        return montoTotal;
    }

    /**
     * Establece el monto total de la liquidación.
     * @param montoTotal Monto total a establecer.
     */
    public void setMontoTotal(java.math.BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    /**
     * Obtiene el identificador único de la liquidación.
     * @return Identificador único.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único de la liquidación.
     * @param id Identificador único a establecer.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el usuario asociado a la liquidación.
     * @return Usuario asociado.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Establece el usuario asociado a la liquidación.
     * @param usuario Usuario a establecer.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene el mes de la liquidación.
     * @return Mes de la liquidación.
     */
    public int getMes() {
        return mes;
    }

    /**
     * Establece el mes de la liquidación.
     * @param mes Mes a establecer.
     */
    public void setMes(int mes) {
        this.mes = mes;
    }

    /**
     * Obtiene el año de la liquidación.
     * @return Año de la liquidación.
     */
    public int getAnio() {
        return anio;
    }

    /**
     * Establece el año de la liquidación.
     * @param anio Año a establecer.
     */
    public void setAnio(int anio) {
        this.anio = anio;
    }

    /**
     * Obtiene la lista de partidas asociadas a la liquidación.
     * @return Lista de partidas.
     */
    public List<Asignacion> getPartidas() {
        return partidas;
    }

    /**
     * Establece la lista de partidas asociadas a la liquidación.
     * @param partidas Lista de partidas a establecer.
     */
    public void setPartidas(List<Asignacion> partidas) {
        this.partidas = partidas;
    }
}
