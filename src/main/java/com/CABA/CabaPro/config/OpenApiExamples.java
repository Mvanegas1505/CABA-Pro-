package com.CABA.CabaPro.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.examples.Example;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * Registers reusable OpenAPI examples for use in controller documentation.
 */
@Configuration
public class OpenApiExamples {

        @Bean
        public Components openApiComponents() {
                Example arbitroExample = new Example()
                                .summary("Ejemplo de árbitro")
                                .value(Map.of(
                                                "id", 1,
                                                "nombre", "Juan",
                                                "apellido", "Pérez",
                                                "email", "juan.perez@example.com",
                                                "roles", List.of("ARBITRO")));

                Example partidoExample = new Example()
                                .summary("Ejemplo de partido")
                                .value(Map.of(
                                                "id", 42,
                                                "fecha", "2025-11-10T16:00:00",
                                                "local", "Club A",
                                                "visitante", "Club B",
                                                "estadio", "Estadio Central",
                                                "estado", "PROGRAMADO"));

                Example torneoExample = new Example()
                                .summary("Ejemplo de torneo")
                                .value(Map.of(
                                                "id", 7,
                                                "nombre", "Torneo Apertura",
                                                "temporada", "2025",
                                                "equipos", 16));

                Example apiResponseExample = new Example()
                                .summary("Respuesta API exitosa")
                                .value(Map.of(
                                                "success", true,
                                                "message", "Operación exitosa",
                                                "data", Map.of("exampleKey", "exampleValue")));

                Example errorResponseExample = new Example()
                                .summary("Respuesta de error estándar")
                                .value(Map.of(
                                                "code", "INTERNAL_SERVER_ERROR",
                                                "message", "Ha ocurrido un error interno",
                                                "details",
                                                Map.of("timestamp", "2025-11-03T21:00:00", "path", "/api/example")));

                Components components = new Components()
                                .addExamples("ArbitroExample", arbitroExample)
                                .addExamples("PartidoExample", partidoExample)
                                .addExamples("TorneoExample", torneoExample)
                                .addExamples("ApiResponseExample", apiResponseExample)
                                .addExamples("ErrorResponseExample", errorResponseExample);

                
                return components;
        }

}
