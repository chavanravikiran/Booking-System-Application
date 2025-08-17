package com.ecommerce.jwt.configuration;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ecommerce.jwt.service.JwtService;
import com.ecommerce.jwt.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private JwtService jwtService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
			FilterChain filterChain)
			throws ServletException, IOException {
		
		final String header= request.getHeader("Authorization");
		
		String jwtToken= null;
		String userName= null;
		
		if(header != null && header.startsWith("Bearer ")) {
			jwtToken= header.substring(7);
			
			try {
				userName= jwtUtil.getUserNameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				System.out.println("Enable to get JWT token");
			}
			catch (ExpiredJwtException e) {
				System.out.println("JWT token is Expired");
			}
		}
		else {
			System.out.println("Jwt Token does not start with Bearer");
		}
		
		
	}

}
