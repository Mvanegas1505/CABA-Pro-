package com.CABA.CabaPro.service;

import com.CABA.CabaPro.model.Liquidacion;
import com.CABA.CabaPro.repository.LiquidacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

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
        try (PDDocument document = new PDDocument(); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            float margin = 50;
            float yStart = PDRectangle.A4.getHeight() - margin;
            float y = yStart;
            float leading = 18f;
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
            contentStream.beginText();
            contentStream.newLineAtOffset(margin, y);
            contentStream.showText("LIQUIDACIÓN DE ARBITRAJE");
            contentStream.endText();
            y -= leading * 2;

            contentStream.setFont(PDType1Font.HELVETICA, 12);
            // Árbitro
            String nombreArbitro = (liquidacion.getUsuario() != null && liquidacion.getUsuario().getNombre() != null)
                    ? liquidacion.getUsuario().getNombre()
                    : "(Sin nombre)";
            contentStream.beginText();
            contentStream.newLineAtOffset(margin, y);
            contentStream.showText("Árbitro: " + nombreArbitro);
            contentStream.endText();
            y -= leading;

            // Período
            String mes = String.valueOf(liquidacion.getMes());
            String anio = String.valueOf(liquidacion.getAnio());
            contentStream.beginText();
            contentStream.newLineAtOffset(margin, y);
            contentStream.showText("Período: " + mes + "/" + anio);
            contentStream.endText();
            y -= leading;

            // Monto
            String monto = liquidacion.getMontoTotal() != null ? liquidacion.getMontoTotal().toString() : "-";
            contentStream.beginText();
            contentStream.newLineAtOffset(margin, y);
            contentStream.showText("Monto Total: $" + monto);
            contentStream.endText();
            y -= leading * 2;

            // Detalle de Partidos
            contentStream.beginText();
            contentStream.newLineAtOffset(margin, y);
            contentStream.showText("Detalle de Partidos:");
            contentStream.endText();
            y -= leading;

            if (liquidacion.getPartidas() != null && !liquidacion.getPartidas().isEmpty()) {
                for (int i = 0; i < liquidacion.getPartidas().size(); i++) {
                    var asignacion = liquidacion.getPartidas().get(i);
                    String texto = "- Partido " + (i + 1) + ": ";
                    if (asignacion != null && asignacion.getPartido() != null) {
                        String torneo = asignacion.getPartido().getTorneo() != null
                                && asignacion.getPartido().getTorneo().getNombre() != null
                                        ? asignacion.getPartido().getTorneo().getNombre()
                                        : "(Sin torneo)";
                        String lugar = asignacion.getPartido().getLugar() != null ? asignacion.getPartido().getLugar()
                                : "(Sin lugar)";
                        texto += "Torneo: " + torneo + ", Lugar: " + lugar;
                    } else {
                        texto += "(Sin datos de partido)";
                    }
                    contentStream.beginText();
                    contentStream.newLineAtOffset(margin, y);
                    contentStream.showText(texto);
                    contentStream.endText();
                    y -= leading;
                    if (y < margin) {
                        contentStream.close();
                        page = new PDPage(PDRectangle.A4);
                        document.addPage(page);
                        contentStream = new PDPageContentStream(document, page);
                        y = yStart;
                    }
                }
            } else {
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, y);
                contentStream.showText("(Sin partidos asignados)");
                contentStream.endText();
                y -= leading;
            }

            contentStream.close();
            document.save(baos);
            return baos.toByteArray();
        } catch (IOException e) {
            // En caso de error, devolver un PDF vacío o mensaje de error
            return new byte[0];
        }
    }
}
