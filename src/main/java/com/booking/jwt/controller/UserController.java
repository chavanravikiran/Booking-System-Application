package com.booking.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.booking.jwt.entity.User;
import com.booking.jwt.service.UserService;

import jakarta.annotation.PostConstruct;


@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostConstruct
	public void initRolesAndUsers() {
//		userService.initRolesAndUser();
	}
	
	@PostMapping("/registerNewUser")
	public User registerNewUser(@RequestBody User user) {
		return userService.registerNewUser(user);
	}
	
	@GetMapping({"/forAdmin"})
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String forAdmin() {
		return "This is accessible for only Admin";
	}
	
	@GetMapping({"/forUser"})
	@PreAuthorize("hasRole('ROLE_USER')")
	public String forUser() {
		return "This is accessible for only User";
	}
}
