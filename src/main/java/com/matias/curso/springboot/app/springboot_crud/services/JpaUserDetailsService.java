package com.matias.curso.springboot.app.springboot_crud.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.matias.curso.springboot.app.springboot_crud.entities.User;
import com.matias.curso.springboot.app.springboot_crud.repositories.UserRepository;


@Service
public class JpaUserDetailsService implements UserDetailsService {


    // esto nos permite obtener el usuario
    @Autowired
    private UserRepository repository;


    //buscamos el usuario de spring security por el username
    @Transactional(readOnly = true) // se define asi por que es una consulta
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       
        Optional<User> userOptional = repository.findByUsername(username);

        if( userOptional.isEmpty() ){


            throw new UsernameNotFoundException(String.format("username %s no existe en el sistema!",  username));


        }


        User user = userOptional.orElseThrow();

        List<GrantedAuthority> authorities = user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getName()))
        .collect(Collectors.toList());

        // se debe llamar al package ya que se se esta usando la clase User
        return new org.springframework.security.core.userdetails.User( user.getUsername(), 
        user.getPassword(), user.isEnabled(),
        true,
        true,
        true,
        authorities);


    }

}
