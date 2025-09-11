package com.ecommerce.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.jwt.entity.ImageModel;
import com.ecommerce.jwt.entity.Product;

@Repository
public interface ImageModelRepository extends JpaRepository<ImageModel, Long>{

}
