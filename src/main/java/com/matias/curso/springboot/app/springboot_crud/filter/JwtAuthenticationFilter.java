package com.matias.curso.springboot.app.springboot_crud.filter;

import java.io.IOException;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matias.curso.springboot.app.springboot_crud.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.matias.curso.springboot.app.springboot_crud.security.TokenJwtConfig;

// este filtro es para autenticar y generar el token
public class JwtAuthenticationFilter  extends UsernamePasswordAuthenticationFilter{

    private AuthenticationManager authenticationManager;


    

   
    



    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        
                User user = null;

                String username = null;
                String password = null;

                try {
                    user = new ObjectMapper().readValue(request.getInputStream(), User.class);

                    username = user.getUsername();
                    password = user.getPassword();


                } catch (StreamReadException e) { // esto pasa cuando no puede pasar los valores del json a la clase user
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (DatabindException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                // esto compara el password con el de la bd
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

                return authenticationManager.authenticate(authenticationToken); // esto llama al jpaUserDetailsServices creado


    }

    //metodo de lo que pasa si todo sale bien
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

                User user = ( User )authResult.getPrincipal(); // user de spring security

                String username = user.getUsername();
                // recibe los roles
                Collection<? extends GrantedAuthority>roles =authResult.getAuthorities(); // crea un collection que acepte todo lo que herede de GrantedAuthority

                Claims claims = Jwts.claims().build(); // nos permite agregar los roles al token

                claims.put("authorities", roles);



                String token = Jwts.builder()
                .subject(username) // asocia el token con el username
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + 3600000)) // establece el tiempo de expiracion del token, el cual es la fecha actual + 1 hora
                .issuedAt(new Date())
                .signWith(TokenJwtConfig.SECRET_KEY) // genera el token con la clave secreta
                .compact(); // esto genera el token

                response.addHeader(TokenJwtConfig.HEADER_AUTHORIZATION, TokenJwtConfig.PREFIX_TOKEN + token); // devuelve el token al cliente o vista
                //Bearer es el tipo de token de jwt

                // creamos el mensaje que se mostrara al cliente
                Map<String, String> body = new HashMap<>();
                body.put("token", token); // aqui va solo el token sin el Bearer
                body.put("username", username); 
                body.put("message", String.format("hola %s has iniciado sesion con exito!", username ) ); // mensaje

                //escribimos el json en la respuesta
                response.getWriter().write(new ObjectMapper().writeValueAsString(body)); // convierte el Map en un JSON
                response.setContentType("application/json");// establece que es un JSON
                response.setStatus(200); // status de correcto
        
    }

    

   

    

}
