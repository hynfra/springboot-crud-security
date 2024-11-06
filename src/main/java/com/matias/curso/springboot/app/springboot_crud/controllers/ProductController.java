package com.matias.curso.springboot.app.springboot_crud.controllers;


import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.matias.curso.springboot.app.springboot_crud.entities.Product;
import com.matias.curso.springboot.app.springboot_crud.services.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products") // las rutas inician con / pero siempre terminan sin /
public class ProductController {

    @Autowired
    private ProductService service; // se debe llamar a la interfaz de services, NO a la implementacion


    //@Autowired
    //private ProductValidation validation;

    @GetMapping // rutea 
    public List<Product> list(){

        return service.findAll();

    }


    // permite visualizar el product
    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable Long id){ // la id se enviara atraves de la url de getmapping, usando PathVariable
        Optional<Product> productOptional =  service.findById(id); // busca el product usandolo en el optional
        
        if(productOptional.isPresent()){ // si se encuentra el producto

            return ResponseEntity.ok(productOptional.orElseThrow()); // da la respuesta de ok y se pasa el productOptional

        }

        return ResponseEntity.notFound().build(); // sino entrega un mensaje de no encontrado

    }

    // crea el producto enviado desde el cliente
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Product product, BindingResult result){

        // llama a la clase personalizada para validar errores
       // validation.validate(product, result);
        if (result.hasFieldErrors()) {

            return validation(result);
            
        }

        
        Product productNew = service.save(product); // guarda el product recibido por parametro

        return ResponseEntity.status( HttpStatus.CREATED ).body( productNew );
    }


    // actualiza el product
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Product product, BindingResult result){ // BindingResult debe estar a la derecha (al lado) del objeto a validar (Product)

        //validation.validate(product, result);
        if (result.hasFieldErrors()) {

            return validation(result);
            
        }


        Optional<Product> productOptional = service.update(id, product);

        if(productOptional.isPresent()){

            return ResponseEntity.status( HttpStatus.CREATED ).body( productOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();

    }

    // permite eliminar el product
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){ // la id se enviara atraves de la url de getmapping, usando PathVariable
        
        Optional<Product> productOptional =  service.delete(id); // busca el product usandolo en el optional
        
        if(productOptional.isPresent()){ // si se encuentra el producto

            return ResponseEntity.ok(productOptional.orElseThrow()); // da la respuesta de ok y se pasa el productOptional

        }

        return ResponseEntity.notFound().build(); // sino entrega un mensaje de no encontrado

    }

    // el <?> indica que puede ser cualquier valor
    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        // por cada campo que ocurra un error, se guada en err y se muestra el campo con su error correspondiente
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors);
    }


}
