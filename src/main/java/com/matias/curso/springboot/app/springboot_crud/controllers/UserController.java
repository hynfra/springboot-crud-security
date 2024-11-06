package com.matias.curso.springboot.app.springboot_crud.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.matias.curso.springboot.app.springboot_crud.entities.User;
import com.matias.curso.springboot.app.springboot_crud.services.UserService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;



@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public List<User> list() {

        return service.findAll();
    };

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result){
        // de vuelve la respuesta de create al body y guuarda el usuario
        if (result.hasFieldErrors()) {

            return validation(result);
            
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user, BindingResult result){
        // de vuelve la respuesta de create al body y guuarda el usuario
        user.setAdmin(false);

        return create(user, result);
    }

      private ResponseEntity<?> validation(BindingResult result) {
        java.util.Map <String, String> errors = new HashMap<>();

        // por cada campo que ocurra un error, se guada en err y se muestra el campo con su error correspondiente
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors);
    }


}
