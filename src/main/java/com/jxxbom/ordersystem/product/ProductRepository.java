package com.jxxbom.ordersystem.product;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Product의 Repository
 */
@Repository
public interface ProductRepository extends CrudRepository<Product, String> {
    
    List<Product> findAll();

}