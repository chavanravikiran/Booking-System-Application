package com.ecommerce.jwt.service;

import java.util.ArrayList;
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
	
	public Product getProductDetailsById(Long productId) {
	   return productRepository.findById(productId).get();
	}
	
	public List<Product> getProductDetails(boolean isSingleProductCheckout, Integer productId) {
		if(isSingleProductCheckout) {
			
			List<Product> list= new ArrayList<>();
			Product product = productRepository.findById(productId.longValue()).orElse(null);
			list.add(product);
			return list;
		}else {
			
		}
		return new ArrayList<>();
	}
}
