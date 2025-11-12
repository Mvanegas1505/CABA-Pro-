package com.CABA.CabaPro.service.location;

import java.util.Optional;

/**
 * Abstracción para resolver coordenadas (geocoding) a partir de una dirección.
 */
public interface LocationService {
    /**
     * Intenta resolver la dirección y devuelve coordenadas si fue posible.
     * @param address dirección libre (lugar)
     * @return Optional con Coordinates si se pudo resolver
     */
    Optional<Coordinates> geocode(String address);
}
