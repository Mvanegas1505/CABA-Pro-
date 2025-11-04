package com.CABA.util.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Valida que una cadena pertenezca a los valores de un Enum dado.
 * Útil cuando el modelo usa String pero queremos validar contra un Enum.
 */
@Documented
@Retention(RUNTIME)
@Target({ FIELD })
@Constraint(validatedBy = OneOfEnumValidator.class)
public @interface OneOfEnum {

    String message() default "Valor no permitido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /** Enum contra el cual se valida. */
    Class<? extends Enum<?>> enumClass();

    /** Si se ignora el case (mayúsculas/minúsculas). */
    boolean ignoreCase() default true;
}
