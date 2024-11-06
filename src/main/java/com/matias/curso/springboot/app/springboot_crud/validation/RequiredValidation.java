package com.matias.curso.springboot.app.springboot_crud.validation;

import org.springframework.util.StringUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

// se pone la clase personalizada  y luego el tipo de dato a validar (String) 
public class RequiredValidation implements ConstraintValidator<IsRequired,String>{

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
       
        return StringUtils.hasText(value); // se debe heredar de psring framework, esto es igual al codigo comentado abajo
         //return (value != null && !value.isEmpty() && value.isBlank()) ;
        
    }

}
