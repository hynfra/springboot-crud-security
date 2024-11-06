package com.matias.curso.springboot.app.springboot_crud;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.matias.curso.springboot.app.springboot_crud.entities.Product;

@Component
public class ProductValidation implements Validator{
// clase personalizada para validar campos
// se establece como Component para poder inyectar despues
    @Override
    public boolean supports(Class<?> clazz) {
        // esto sirve para que la clase entity pueda validar
        return Product.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
       
        Product product =(Product) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", null , "es requerido!");
        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "NotBlank.product.description");

        if(product.getDescription() == null || product.getDescription().isBlank()) {

            errors.rejectValue("description", null, "es requerido, porfavor");


        }

        if (product.getPrice() == null ) {


            errors.rejectValue("price", null, "No puede ser nulo, ok!");
            
        }else if( product.getPrice() < 500){

            errors.rejectValue("price", null, "No puede ser menor que 500, ok!");
        }
    }

}
