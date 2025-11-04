package com.CABA.CabaPro.exception;

import java.util.Collections;
import java.util.Map;

/**
 * Excepción de validación de reglas de negocio con detalles por campo.
 */
public class ValidationException extends RuntimeException {
    private final Map<String, Object> errors;

    public ValidationException(String message) {
        super(message);
        this.errors = Collections.emptyMap();
    }

    public ValidationException(String message, Map<String, Object> errors) {
        super(message);
        this.errors = errors != null ? errors : Collections.emptyMap();
    }

    public Map<String, Object> getErrors() {
        return errors;
    }
}
