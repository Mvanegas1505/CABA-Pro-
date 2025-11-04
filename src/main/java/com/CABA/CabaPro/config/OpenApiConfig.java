package com.CABA.CabaPro.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "CabaPro API", version = "v1", description = "API REST para CabaPro — gestión de árbitros, partidos, torneos y liquidaciones.\n\n"
        +
        "Exposición de endpoints para roles: ARBITRO y ADMIN.\n" +
        "Principales recursos: /arbitro/**, /admin/**, /user/**.\n\n" +
        "Formato de respuestas: objetos JSON con estructura {success, message, data, timestamp}.\n\n" +
        "Usar el parámetro ?lang=es|en para localización cuando aplique.", contact = @Contact(name = "CabaPro Team", email = "soporte@cabapro.local")), servers = {
                @Server(description = "Local (desarrollo)", url = "http://localhost:8080"),
                @Server(description = "Staging", url = "https://staging.cabapro.local"),
                @Server(description = "Producción", url = "https://cabapro.example.com")
        }, tags = {
                @Tag(name = "Auth", description = "Endpoints de autenticación y sesión"),
                @Tag(name = "Arbitro", description = "Operaciones para árbitros: dashboard, partidos, liquidaciones"),
                @Tag(name = "Admin", description = "Operaciones administrativas: torneos, partidos, asignaciones"),
                @Tag(name = "Usuarios", description = "Gestión de usuarios/perfiles")
        })
// Security: la aplicación usa login por formulario y sesión (JSESSIONID).
// Documentamos el cookie-based auth.
@SecurityScheme(name = "JSESSIONID", type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.COOKIE, paramName = "JSESSIONID", description = "Autenticación basada en sesión (cookie JSESSIONID) generada por el login\nUsar /login para obtener sesión y enviar cookie en llamadas subsecuentes")
// También documentamos un posible esquema Bearer (por si se agrega token-based
// auth en el futuro)
@SecurityScheme(name = "BearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT", description = "Bearer token (JWT) — opcional si se habilita en el futuro")
public class OpenApiConfig {

    /**
     * Configuración personalizada de OpenAPI
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new io.swagger.v3.oas.models.Components()
                        // Respuestas reutilizables
                        .addResponses("NotFound", createNotFoundResponse())
                        .addResponses("BadRequest", createBadRequestResponse())
                        .addResponses("InternalServerError",
                                createInternalServerErrorResponse())
                        .addResponses("Unauthorized", createUnauthorizedResponse())
                        .addResponses("Forbidden", createForbiddenResponse())

                        // Esquemas reutilizables
                        .addSchemas("ValidationError", createValidationErrorSchema())
                        .addSchemas("BusinessError", createBusinessErrorSchema()));
    }

    /**
     * Respuesta estándar para errores 404
     */
    private ApiResponse createNotFoundResponse() {
        return new ApiResponse()
                .description("Recurso no encontrado")
                .content(new Content()
                        .addMediaType("application/json", new MediaType()
                                .example(createErrorExample(
                                        "PRODUCT_NOT_FOUND",
                                        "Producto no encontrado con ID: 123",
                                        "El producto solicitado no existe o ha sido eliminado"))));
    }

    /**
     * Respuesta estándar para errores 400
     */
    private ApiResponse createBadRequestResponse() {
        return new ApiResponse()
                .description("Solicitud inválida")
                .content(new Content()
                        .addMediaType("application/json", new MediaType()
                                .example(createValidationErrorExample())));
    }

    /**
     * Respuesta estándar para errores 500
     */
    private ApiResponse createInternalServerErrorResponse() {
        return new ApiResponse()
                .description("Error interno del servidor")
                .content(new Content()
                        .addMediaType("application/json", new MediaType()
                                .example(createErrorExample(
                                        "INTERNAL_SERVER_ERROR",
                                        "Ha ocurrido un error interno en el servidor",
                                        "Error inesperado durante el procesamiento"))));
    }

    /**
     * Respuesta estándar para errores 401
     */
    private ApiResponse createUnauthorizedResponse() {
        return new ApiResponse()
                .description("No autorizado - Token inválido o expirado")
                .content(new Content()
                        .addMediaType("application/json", new MediaType()
                                .example(createErrorExample(
                                        "UNAUTHORIZED",
                                        "Token de acceso inválido o expirado",
                                        "Proporcione un token válido en el header Authorization"))));
    }

    /**
     * Respuesta estándar para errores 403
     */
    private ApiResponse createForbiddenResponse() {
        return new ApiResponse()
                .description("Acceso prohibido - Permisos insuficientes")
                .content(new Content()
                        .addMediaType("application/json", new MediaType()
                                .example(createErrorExample(
                                        "FORBIDDEN",
                                        "No tiene permisos para realizar esta operación",
                                        "Su nivel de acceso no permite esta acción"))));
    }

    /**
     * Esquema para errores de validación
     */
    @SuppressWarnings("unchecked")
    private io.swagger.v3.oas.models.media.Schema<Object> createValidationErrorSchema() {
        return new io.swagger.v3.oas.models.media.Schema<Object>()
                .type("object")
                .description("Error de validación con detalles específicos por campo")
                .addProperty("success", new io.swagger.v3.oas.models.media.Schema<Object>()
                        .type("boolean")
                        .example(false))
                .addProperty("message", new io.swagger.v3.oas.models.media.Schema<Object>()
                        .type("string")
                        .example("Error de validación"))
                .addProperty("timestamp", new io.swagger.v3.oas.models.media.Schema<Object>()
                        .type("string")
                        .format("date-time"))
                .addProperty("data", new io.swagger.v3.oas.models.media.Schema<Object>()
                        .type("object")
                        .description("Detalles del error por campo"));
    }

    /**
     * Esquema para errores de negocio
     */
    @SuppressWarnings("unchecked")
    private io.swagger.v3.oas.models.media.Schema<Object> createBusinessErrorSchema() {
        return new io.swagger.v3.oas.models.media.Schema<Object>()
                .type("object")
                .description("Error de lógica de negocio")
                .addProperty("success", new io.swagger.v3.oas.models.media.Schema<Object>()
                        .type("boolean")
                        .example(false))
                .addProperty("message", new io.swagger.v3.oas.models.media.Schema<Object>()
                        .type("string")
                        .example("Error de negocio"))
                .addProperty("errorCode", new io.swagger.v3.oas.models.media.Schema<Object>()
                        .type("string")
                        .example("BUSINESS_RULE_VIOLATION"));
    }

    /**
     * Ejemplo genérico de error
     */
    private Object createErrorExample(String code, String message, String detail) {
        return java.util.Map.of(
                "success", false,
                "message", message,
                "timestamp", "2024-01-15T10:30:00",
                "statusCode", 404,
                "data", java.util.Map.of(
                        "errorCode", code,
                        "message", message,
                        "details", java.util.Map.of("info", detail),
                        "timestamp", "2024-01-15T10:30:00"));
    }

    /**
     * Ejemplo de error de validación
     */
    private Object createValidationErrorExample() {
        return java.util.Map.of(
                "success", false,
                "message", "Error de validación en los datos enviados",
                "timestamp", "2024-01-15T10:30:00",
                "statusCode", 400,
                "data", java.util.Map.of(
                        "errorCode", "VALIDATION_ERROR",
                        "message", "Error de validación en los datos enviados",
                        "details", java.util.Map.of(
                                "name", "El nombre del producto es obligatorio",
                                "price", "El precio debe ser mayor a 0",
                                "category", "La categoría es obligatoria"),
                        "timestamp", "2024-01-15T10:30:00"));
    }
}
