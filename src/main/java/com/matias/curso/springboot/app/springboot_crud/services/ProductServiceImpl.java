package com.matias.curso.springboot.app.springboot_crud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.matias.curso.springboot.app.springboot_crud.entities.Product;
import com.matias.curso.springboot.app.springboot_crud.repositories.ProductRepository;

@Service // establece que esto es un component y representa la logica de negocio
public class ProductServiceImpl implements ProductService{

// esta clase se encarga de usar la interfaz de services
// ademas usa la interfaz de repositories para usar los comandos basicos de crud

    @Autowired
    private ProductRepository repository;


    @Transactional(readOnly = true)
    @Override
    public List<Product> findAll() {
       
        return (List<Product>) repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Product> findById(Long id) {
        
        return repository.findById(id);
    }


    @Transactional // debe heredar de spring framework
    @Override
    public Optional<Product> delete(Long id) {
        // busca el product por el id
        Optional<Product> productOptional = repository.findById(id);

        // si lo encuentra lo borra
        productOptional.ifPresent(productDb -> {
            
            repository.delete(productDb);

        });
        return productOptional;
    }



    @Transactional
    @Override
    public Product save(Product product) {
        
        repository.save(product);
        return product;
    }

    @Override
    @Transactional
    public Optional<Product>  update(Long id, Product product) {
         // busca el product por el id
         Optional<Product> productOptional = repository.findById(id);

         // se cambia a if porque la expresion lambda no puede retornar valores
         if(productOptional.isPresent()){ // si encuentra el product aplica lo siguiente
            Product productDb = productOptional.orElseThrow(); // guarda el product si es que lo encuentra
            // establece lo datos del product ingresados
            productDb.setSku(product.getSku());
            productDb.setName(product.getName());
            productDb.setDescription(product.getDescription());
            productDb.setPrice(product.getPrice());
            // devuelve el product como OPTIONAL y lo guarda en la db
            return Optional.of(repository.save(productDb));
         }
         return productOptional;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsBySku(String sku) {
        return repository.existsBySku(sku);
    }

}
