package com.ecommerce.jwt.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderProductQuantity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long orderProductQuantityId;
	private Long productId;
	private Integer quantity;
}
