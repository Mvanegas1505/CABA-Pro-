package com.CABA.CabaPro.exception;

/**
 * Excepción para conflictos por recurso duplicado/ya existente.
 */
public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
