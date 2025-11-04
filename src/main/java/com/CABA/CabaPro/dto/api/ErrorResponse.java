package com.CABA.CabaPro.dto.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * DTO estándar para respuestas de error en CabaPro.
 */

@Schema(description = "Respuesta de error estándar de la API")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    @Schema(description = "Código de error de negocio o técnico", example = "VALIDATION_ERROR")
    private String errorCode;

    @Schema(description = "Mensaje principal del error", example = "Error de validación en los datos enviados")
    private String message;

    @Schema(description = "Detalles adicionales del error (por campo o info específica)")
    private Map<String, Object> details;

    @Schema(description = "Ruta del request donde ocurrió el error", example = "/arbitro/liquidaciones/generar")
    private String path;

    @Schema(description = "Método HTTP de la solicitud", example = "POST")
    private String method;

    @Schema(description = "Momento en que ocurrió el error", example = "2025-11-03T10:30:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    public ErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(String errorCode, String message) {
        this();
        this.errorCode = errorCode;
        this.message = message;
    }

    public ErrorResponse(String errorCode, String message, Map<String, Object> details) {
        this(errorCode, message);
        this.details = details;
    }

    public ErrorResponse(String errorCode, String message, Map<String, Object> details,
            String path, String method, LocalDateTime timestamp) {
        this.errorCode = errorCode;
        this.message = message;
        this.details = details;
        this.path = path;
        this.method = method;
        this.timestamp = timestamp != null ? timestamp : LocalDateTime.now();
    }

    // Factory helpers
    public static ErrorResponse of(String code, String message) {
        return new ErrorResponse(code, message);
    }

    public static ErrorResponse of(String code, String message, Map<String, Object> details) {
        return new ErrorResponse(code, message, details);
    }

    public static ErrorResponse validation(Map<String, Object> fieldErrors) {
        return new ErrorResponse("VALIDATION_ERROR", "Error de validación", fieldErrors);
    }

    // Getters & Setters
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getDetails() {
        return details;
    }

    public void setDetails(Map<String, Object> details) {
        this.details = details;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
