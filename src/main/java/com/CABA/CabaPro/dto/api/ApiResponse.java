package com.CABA.CabaPro.dto.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * Respuesta estándar de la API de CabaPro
 * Estructura consistente para respuestas exitosas y de error.
 */
@Schema(description = "Respuesta estándar de la API")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    @Schema(description = "Indica si la operación fue exitosa", example = "true")
    private boolean success;

    @Schema(description = "Mensaje descriptivo de la operación", example = "Operación completada exitosamente")
    private String message;

    @Schema(description = "Datos de la respuesta")
    private T data;

    @Schema(description = "Timestamp de la respuesta", example = "2024-01-15T10:30:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    @Schema(description = "Código de estado HTTP", example = "200")
    private int statusCode;

    // Constructores
    public ApiResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse(boolean success, String message, T data, int statusCode) {
        this();
        this.success = success;
        this.message = message;
        this.data = data;
        this.statusCode = statusCode;
    }

    // Métodos de utilidad
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, message, data, 200);
    }

    public static <T> ApiResponse<T> success(T data) {
        return success(data, "Operación completada exitosamente");
    }

    public static <T> ApiResponse<T> error(String message, int statusCode) {
        return new ApiResponse<>(false, message, null, statusCode);
    }

    public static <T> ApiResponse<T> error(String message) {
        return error(message, 500);
    }

    /**
     * Crea una respuesta de error incluyendo un payload de error (p. ej.
     * ErrorResponse)
     */
    public static <T> ApiResponse<T> error(T data, String message, int statusCode) {
        return new ApiResponse<>(false, message, data, statusCode);
    }

    public static <T> ApiResponse<T> error(T data, String message) {
        return error(data, message, 500);
    }

    // Getters y Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
