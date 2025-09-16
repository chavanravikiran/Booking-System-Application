package com.booking.jwt.service;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.booking.jwt.entity.Role;
import com.booking.jwt.entity.RolesEnum;
import com.booking.jwt.entity.User;
import com.booking.jwt.repository.RoleRepository;
import com.booking.jwt.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User registeredNewUser(User user) {
		return userRepository.save(user);
	}
	
	public User registerNewUser(User user) {
		
		//Role role= roleRepository.findById("User").get();
		Role role = roleRepository.findByRoleName("User")
			    .orElseThrow(() -> new RuntimeException("Role not found: User"));
		
		Set<Role> roles= new HashSet<>();
		roles.add(role);
		user.setRole(roles);
		//TODO if User{
		user.setRoles(RolesEnum.ROLE_USER);
		//	}
//		else {
//			user.setRoles(RolesEnum.ROLE_ADMIN);
//		}
		user.setPassword(getEncodedPassword(user.getPassword()));
		
		return userRepository.save(user);
	}
	
	public void initRolesAndUser() {
		
		Role adminRole= new Role();
		adminRole.setRoleName(RolesEnum.ROLE_ADMIN.toString());
		adminRole.setRoleDescription("Admin role");
		roleRepository.save(adminRole);
		
		Role userRole= new Role();
		userRole.setRoleName(RolesEnum.ROLE_USER.toString());
		userRole.setRoleDescription("User Role");
		roleRepository.save(userRole);
		
		User adminUser= new User();
		adminUser.setUserName("ADMIN");
		adminUser.setFirstName("Multigenesys");
		adminUser.setLastName("Hinjewadi");
		adminUser.setPassword(getEncodedPassword("Admin@123"));		
		Set<Role> adminRoles = new HashSet<>();
		adminRoles.add(adminRole);
		adminUser.setRoles(RolesEnum.ROLE_ADMIN);
		adminUser.setRole(adminRoles);
		userRepository.save(adminUser);
		
		User user= new User();
		user.setUserName("ravikiran123");
		user.setFirstName("ravikiran");
		user.setLastName("chavan");
		user.setPassword(getEncodedPassword("Ravi@1997"));
		Set<Role> userRoles = new HashSet<>();
		userRoles.add(userRole);
		user.setRoles(RolesEnum.ROLE_USER);
		user.setRole(userRoles);
		userRepository.save(user);
	}
	
	public String getEncodedPassword(String password) {
		return passwordEncoder.encode(password);
	}
}
