package com.CABA.CabaPro.service.location;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * Implementación de LocationService que utiliza Mapbox Geocoding API.
 */
public class MapboxLocationService implements LocationService {

    private final RestTemplate restTemplate;
    private final String token;
    private final ObjectMapper mapper = new ObjectMapper();

    public MapboxLocationService(RestTemplate restTemplate, String token) {
        this.restTemplate = restTemplate;
        this.token = token != null ? token : "";
    }

    @Override
    public Optional<Coordinates> geocode(String address) {
        try {
            String q = URLEncoder.encode(address, StandardCharsets.UTF_8);
            String url = "https://api.mapbox.com/geocoding/v5/mapbox.places/" + q + ".json?access_token=" + token + "&limit=1&language=es";
            String resp = restTemplate.getForObject(url, String.class);
            JsonNode root = mapper.readTree(resp);
            JsonNode features = root.path("features");
            if (features.isArray() && features.size() > 0) {
                JsonNode first = features.get(0);
                JsonNode center = first.path("center");
                if (center.isArray() && center.size() >= 2) {
                    double lon = center.get(0).asDouble();
                    double lat = center.get(1).asDouble();
                    return Optional.of(new Coordinates(lat, lon));
                }
            }
        } catch (Exception e) {
            // log if available; evita romper la app
            System.err.println("Mapbox geocode error: " + e.getMessage());
        }
        return Optional.empty();
    }
}
