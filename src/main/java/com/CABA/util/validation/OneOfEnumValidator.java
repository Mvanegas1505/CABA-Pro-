package com.CABA.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Implementación del validador para {@link OneOfEnum}.
 * Verifica que una cadena coincida con alguno de los nombres del Enum
 * configurado.
 */
public class OneOfEnumValidator implements ConstraintValidator<OneOfEnum, String> {

    private Class<? extends Enum<?>> enumClass;
    private boolean ignoreCase;

    @Override
    public void initialize(OneOfEnum constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
        this.ignoreCase = constraintAnnotation.ignoreCase();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            // Deja que otras anotaciones (@NotBlank) se encarguen si es requerido
            return true;
        }
        for (Enum<?> constant : enumClass.getEnumConstants()) {
            String name = constant.name();
            if (ignoreCase ? name.equalsIgnoreCase(value) : name.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
