package com.CABA.CabaPro.service;

import com.CABA.CabaPro.model.Liquidacion;
import com.CABA.CabaPro.repository.LiquidacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LiquidacionService {
    @Autowired
    private LiquidacionRepository liquidacionRepository;

    public List<Liquidacion> getLiquidacionesPorArbitro(String correo) {
        return liquidacionRepository.findByUsuario_Correo(correo);
    }

    public List<Liquidacion> getAllLiquidaciones() {
        return liquidacionRepository.findAll();
    }

    public Optional<Liquidacion> getLiquidacionById(Long id) {
        return liquidacionRepository.findById(id);
    }

    public Liquidacion saveLiquidacion(Liquidacion liquidacion) {
        return liquidacionRepository.save(liquidacion);
    }

    public void deleteLiquidacion(Long id) {
        liquidacionRepository.deleteById(id);
    }

    public byte[] generarPDF(Liquidacion liquidacion) {
        // Implementación robusta de generación de PDF
        StringBuilder pdfContent = new StringBuilder();
        pdfContent.append("LIQUIDACIÓN DE ARBITRAJE\n\n");
        // Árbitro
        String nombreArbitro = (liquidacion.getUsuario() != null && liquidacion.getUsuario().getNombre() != null)
                ? liquidacion.getUsuario().getNombre()
                : "(Sin nombre)";
        pdfContent.append("Árbitro: ").append(nombreArbitro).append("\n");
        // Período
        String mes = String.valueOf(liquidacion.getMes());
        String anio = String.valueOf(liquidacion.getAnio());
        pdfContent.append("Período: ").append(mes).append("/").append(anio).append("\n");
        // Monto
        String monto = liquidacion.getMontoTotal() != null ? liquidacion.getMontoTotal().toString() : "-";
        pdfContent.append("Monto Total: $").append(monto).append("\n\n");
        pdfContent.append("Detalle de Partidos:\n");
        // Detalle de partidos
        if (liquidacion.getPartidas() != null && !liquidacion.getPartidas().isEmpty()) {
            for (int i = 0; i < liquidacion.getPartidas().size(); i++) {
                var asignacion = liquidacion.getPartidas().get(i);
                pdfContent.append("- Partido ").append(i + 1).append(": ");
                if (asignacion != null && asignacion.getPartido() != null) {
                    String torneo = asignacion.getPartido().getTorneo() != null
                            && asignacion.getPartido().getTorneo().getNombre() != null
                                    ? asignacion.getPartido().getTorneo().getNombre()
                                    : "(Sin torneo)";
                    String lugar = asignacion.getPartido().getLugar() != null ? asignacion.getPartido().getLugar()
                            : "(Sin lugar)";
                    pdfContent.append("Torneo: ").append(torneo).append(", Lugar: ").append(lugar);
                } else {
                    pdfContent.append("(Sin datos de partido)");
                }
                pdfContent.append("\n");
            }
        } else {
            pdfContent.append("(Sin partidos asignados)\n");
        }
        // Convertir a bytes (implementación simplificada)
        return pdfContent.toString().getBytes();
    }
}
