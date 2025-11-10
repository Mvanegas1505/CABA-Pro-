package com.CABA.CabaPro.service.location;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * Implementación de LocationService que utiliza Nominatim (OpenStreetMap) para geocoding.
 */
public class NominatimLocationService implements LocationService {

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    public NominatimLocationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<Coordinates> geocode(String address) {
        try {
            String q = URLEncoder.encode(address, StandardCharsets.UTF_8);
            String url = "https://nominatim.openstreetmap.org/search?format=json&q=" + q + "&limit=1";
            String resp = restTemplate.getForObject(url, String.class);
            JsonNode root = mapper.readTree(resp);
            if (root.isArray() && root.size() > 0) {
                JsonNode first = root.get(0);
                double lat = Double.parseDouble(first.path("lat").asText());
                double lon = Double.parseDouble(first.path("lon").asText());
                return Optional.of(new Coordinates(lat, lon));
            }
        } catch (Exception e) {
            System.err.println("Nominatim geocode error: " + e.getMessage());
        }
        return Optional.empty();
    }
}
