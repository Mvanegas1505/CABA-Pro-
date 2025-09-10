package com.CABA.CabaPro.model;

import jakarta.persistence.*;
import java.util.List;
import com.CABA.CabaPro.model.Usuario;
import com.CABA.CabaPro.model.Asignacion;

@Entity
public class Liquidacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private int mes;
    private int anio;

    @OneToMany
    @JoinColumn(name = "liquidacion_id")
    private List<Asignacion> partidas;

    private java.math.BigDecimal montoTotal;
    // Métodos del diagrama
    public double calcularTotal() {
        // Sumar los montos de las asignaciones
        double total = 0;
        if (partidas != null) {
            for (Asignacion asignacion : partidas) {
                // Aquí deberías obtener el monto de la tarifa asociada a la asignación
                // total += asignacion.getTarifa().getMonto();
            }
        }
        return total;
    }

    public void generarPDF() {
        // Implementar lógica para generar PDF
    }

    // Getters y setters
    public java.math.BigDecimal getMontoTotal() { return montoTotal; }
    public void setMontoTotal(java.math.BigDecimal montoTotal) { this.montoTotal = montoTotal; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public int getMes() { return mes; }
    public void setMes(int mes) { this.mes = mes; }

    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }

    public List<Asignacion> getPartidas() { return partidas; }
    public void setPartidas(List<Asignacion> partidas) { this.partidas = partidas; }
}
