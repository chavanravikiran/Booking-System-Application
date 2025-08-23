package com.ecommerce.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.jwt.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
