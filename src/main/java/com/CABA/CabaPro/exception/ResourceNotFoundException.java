package com.CABA.CabaPro.exception;

/**
 * Excepción para recursos no encontrados en la API.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
