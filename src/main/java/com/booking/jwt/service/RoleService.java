package com.booking.jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.jwt.entity.Role;
import com.booking.jwt.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	public Role createNewRole(Role role) {
	  return roleRepository.save(role);
	}
}
