package com.matias.curso.springboot.app.springboot_crud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {


    @Bean
    PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        // deja publico la ruta users

        //authorizeHttpRequests permite autorizar o denegar permisos
        //damos "/users" para autorizar
        //entrega los permisos a cualquier peticion que este autenticada
        // csrf es un token que se usa como seguridad extra
        // el usuario no queda autenticado en una sesion http por eso se deja sin estado
        return http.authorizeHttpRequests( (authz) -> authz
        .requestMatchers(HttpMethod.GET,"/api/users").permitAll() // permite listar
        .requestMatchers(HttpMethod.POST,"/api/users/register").permitAll() // permite registrar
        .anyRequest()
        .authenticated() )
        .csrf(config -> config.disable())
        .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS) )
        .build();

    }

}
