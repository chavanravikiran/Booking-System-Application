package com.ecommerce.jwt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.jwt.configuration.JwtRequestFilter;
import com.ecommerce.jwt.entity.OrderDetail;
import com.ecommerce.jwt.entity.OrderInput;
import com.ecommerce.jwt.entity.OrderProductQuantity;
import com.ecommerce.jwt.entity.Product;
import com.ecommerce.jwt.entity.User;
import com.ecommerce.jwt.repository.OrderDetailRepository;
import com.ecommerce.jwt.repository.ProductRepository;
import com.ecommerce.jwt.repository.UserRepository;

@Service
public class OrderDetailService {

	public static final String ORDER_PLACED= "Placed";
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public void placeOrder(OrderInput orderInput) {
		List<OrderProductQuantity> productQuantityList= orderInput.getOrderProductQuantities();
		
		String currentUser= JwtRequestFilter.CURRENT_USER;
		User user=	userRepository.findByUserName(currentUser).get();
			
		for(OrderProductQuantity o: productQuantityList) {
			Product product= productRepository.findById(o.getProductId()).get();
			
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrderFullName(orderInput.getFullName());
			orderDetail.setOrderFullAddres(orderInput.getFullAddres());
			orderDetail.setOrderContactNumber(orderInput.getContactNumber());
			orderDetail.setOrderAlternameContactNumber(orderInput.getAlternateContactNumber());
			orderDetail.setOrderStatus(ORDER_PLACED);
			orderDetail.setOrderAmount(product.getProductActualPrice() * o.getQuantity());
			orderDetail.setProduct(product);
			orderDetail.setUser(user);
			
			orderDetailRepository.save(orderDetail);
		}
	}
}
