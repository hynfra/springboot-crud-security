package com.matias.curso.springboot.app.springboot_crud.security;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;

public class TokenJwtConfig {


    public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build(); // establece la contraseña secreta


    public static final String PREFIX_TOKEN = "Bearer ";

    public static final String HEADER_AUTHORIZATION = "Authorization";

}
