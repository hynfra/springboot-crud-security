package com.matias.curso.springboot.app.springboot_crud.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy =  ExistByUsernameValidation.class)
@Target(ElementType.FIELD) //hacia donde se dirige, hacia un campo
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistByUsername {
// codigo sacado de la clase blank
    String message() default "ya existe en la base de datos, escoja otro username";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

}
