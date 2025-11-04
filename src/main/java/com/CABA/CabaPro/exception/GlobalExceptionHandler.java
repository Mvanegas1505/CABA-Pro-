package com.CABA.CabaPro.exception;

import com.CABA.CabaPro.dto.api.ApiResponse;
import com.CABA.CabaPro.dto.api.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Manejo global de excepciones para toda la aplicación CabaPro.
 * Estandariza las respuestas de error usando ApiResponse<ErrorResponse>.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Maneja errores de validación de @Valid en request body (Bean Validation).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        logger.warn("Error de validación: {}", ex.getMessage());

        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = error instanceof FieldError fe ? fe.getField() : error.getObjectName();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponse errorResponse = new ErrorResponse(
                "VALIDATION_ERROR",
                "Error de validación en los datos enviados",
                errors,
                null,
                null,
                LocalDateTime.now());

        return ResponseEntity.badRequest()
                .body(ApiResponse.error(errorResponse, "Datos de entrada inválidos", 400));
    }

    /**
     * Maneja errores de validación de @Validated en parámetros
     * (ConstraintViolation).
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleConstraintViolation(ConstraintViolationException ex) {
        logger.warn("Error de validación de parámetros: {}", ex.getMessage());

        Map<String, Object> errors = new HashMap<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        }

        ErrorResponse errorResponse = new ErrorResponse(
                "PARAMETER_VALIDATION_ERROR",
                "Error de validación en parámetros",
                errors,
                null,
                null,
                LocalDateTime.now());

        return ResponseEntity.badRequest()
                .body(ApiResponse.error(errorResponse, "Parámetros inválidos", 400));
    }

    /**
     * Recurso no encontrado.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleNotFound(ResourceNotFoundException ex, WebRequest request) {
        logger.warn("Recurso no encontrado: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                "RESOURCE_NOT_FOUND",
                ex.getMessage(),
                Map.of("path", request.getDescription(false).replace("uri=", "")),
                null,
                null,
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(errorResponse, "Recurso no encontrado", 404));
    }

    /**
     * Recurso duplicado/conflicto.
     */
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleAlreadyExists(ResourceAlreadyExistsException ex) {
        logger.warn("Recurso ya existe: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                "RESOURCE_ALREADY_EXISTS",
                ex.getMessage(),
                Map.of("suggestion", "Verifique si ya existe y considere actualizarlo"),
                null,
                null,
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(errorResponse, "Conflicto de recursos", 409));
    }

    /**
     * Validación personalizada a nivel de negocio.
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleCustomValidation(ValidationException ex) {
        logger.warn("Error de validación personalizado: {}", ex.getMessage());

        Map<String, Object> details = ex.getErrors() != null ? new HashMap<>(ex.getErrors()) : Map.of();
        ErrorResponse errorResponse = new ErrorResponse(
                "CUSTOM_VALIDATION_ERROR",
                ex.getMessage(),
                details,
                null,
                null,
                LocalDateTime.now());

        return ResponseEntity.badRequest()
                .body(ApiResponse.error(errorResponse, "Error de validación", 400));
    }

    /**
     * Tipo de dato incorrecto en parámetros.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        logger.warn("Error de tipo de dato: {}", ex.getMessage());

        Class<?> requiredType = ex.getRequiredType();
        String expectedTypeName = requiredType != null ? requiredType.getSimpleName() : "unknown";
        String message = String.format("El parámetro '%s' debe ser de tipo %s", ex.getName(), expectedTypeName);

        ErrorResponse errorResponse = new ErrorResponse(
                "TYPE_MISMATCH_ERROR",
                message,
                Map.of("parameter", ex.getName(), "expectedType", expectedTypeName),
                null,
                null,
                LocalDateTime.now());

        return ResponseEntity.badRequest()
                .body(ApiResponse.error(errorResponse, "Tipo de dato incorrecto", 400));
    }

    /**
     * JSON malformado en el cuerpo de la petición.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        logger.warn("Error de formato JSON: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                "MALFORMED_JSON",
                "El formato JSON enviado es inválido",
                Map.of("suggestion", "Verifique la sintaxis JSON del cuerpo de la petición"),
                null,
                null,
                LocalDateTime.now());

        return ResponseEntity.badRequest()
                .body(ApiResponse.error(errorResponse, "Formato JSON inválido", 400));
    }

    /**
     * Argumentos ilegales (dominio/validaciones simples).
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleIllegalArgument(IllegalArgumentException ex) {
        logger.warn("Argumento ilegal: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                "ILLEGAL_ARGUMENT",
                ex.getMessage(),
                Map.of("suggestion", "Verifique los valores enviados en la petición"),
                null,
                null,
                LocalDateTime.now());

        return ResponseEntity.badRequest()
                .body(ApiResponse.error(errorResponse, "Argumento inválido", 400));
    }

    /**
     * Fallback para cualquier excepción no controlada.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleGlobalException(Exception ex, WebRequest request) {
        logger.error("Error interno no controlado: {}", ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                "INTERNAL_SERVER_ERROR",
                "Ha ocurrido un error interno en el servidor",
                Map.of(
                        "timestamp", LocalDateTime.now().toString(),
                        "path", request.getDescription(false).replace("uri=", "")),
                null,
                null,
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(errorResponse, "Error interno del servidor", 500));
    }
}
