package com.matias.curso.springboot.app.springboot_crud.repositories;

import org.springframework.data.repository.CrudRepository;

import com.matias.curso.springboot.app.springboot_crud.entities.Product;

public interface ProductRepository extends CrudRepository<Product,Long> {

    boolean existsBySku(String sku); // verifica si existe el campo por el nombre, en este caso es Sku
}
