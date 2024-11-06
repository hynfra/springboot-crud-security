package com.matias.curso.springboot.app.springboot_crud.services;

import java.util.ArrayList;
import java.util.Optional;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.matias.curso.springboot.app.springboot_crud.entities.Role;
import com.matias.curso.springboot.app.springboot_crud.entities.User;
import com.matias.curso.springboot.app.springboot_crud.repositories.RoleRepository;
import com.matias.curso.springboot.app.springboot_crud.repositories.UserRepository;


@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository repository;


    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    @Transactional(readOnly = true) // con readOnly nos aseguramos que este metodo solo lea la informacion pedida de la bd
    public List<User> findAll() {

        return (List<User>) repository.findAll();
       
    }

    @Override
    @Transactional
    public User save(User user) {


        // ROLE_USER se encuentra en la bd
        Optional<Role> OptionalRoleUser = roleRepository.findByName("ROLE_USER");

        ArrayList<Role> roles = new ArrayList<>();

        // si esta presente le agrega el Role usuario
        OptionalRoleUser.ifPresent(role-> roles.add(role));

        if(user.isAdmin()){

            Optional<Role> optionalRoleAdmin = roleRepository.findByName("ROLE_ADMIN");
            optionalRoleAdmin.ifPresent(role -> roles.add(role));

        }

        user.setRoles(roles);

        // codificamos el password 
        String passwordEncoded = passwordEncoder.encode(user.getPassword());

        user.setPassword(passwordEncoded);

        return repository.save(user);

    }

    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

}
