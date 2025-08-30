package com.ecommerce.jwt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.jwt.entity.Product;
import com.ecommerce.jwt.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	public Product addNewProduct(Product product) {
		return productRepository.save(product);
	}
	
	public List<Product> getAllProducts(){
		return (List<Product>) productRepository.findAll();
	}
	
	public void deleteProductDetails(Long productId) {
		productRepository.deleteById(productId);
	}
}
