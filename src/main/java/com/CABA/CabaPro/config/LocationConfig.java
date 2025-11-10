package com.CABA.CabaPro.config;

import com.CABA.CabaPro.service.location.LocationService;
import com.CABA.CabaPro.service.location.MapboxLocationService;
import com.CABA.CabaPro.service.location.NominatimLocationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class LocationConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * Devuelve la implementación de LocationService según la propiedad app.location.provider.
     * Valores: mapbox (por defecto), nominatim
     */
    @Bean
    public LocationService locationService(@Value("${app.location.provider:mapbox}") String provider,
                                           RestTemplate restTemplate,
                                           @Value("${mapbox.token:}") String mapboxToken) {
        if ("nominatim".equalsIgnoreCase(provider)) {
            return new NominatimLocationService(restTemplate);
        }
        // por defecto Mapbox
        return new MapboxLocationService(restTemplate, mapboxToken);
    }
}
