package com.booking.jwt.service;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.booking.jwt.entity.JwtRequest;
import com.booking.jwt.entity.JwtResponse;
import com.booking.jwt.repository.UserRepository;
import com.booking.jwt.util.JwtUtil;

@Service
public class JwtService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception{
		String userName= jwtRequest.getUserName();
		String userPassword= jwtRequest.getPassword();
		authenticate(userName, userPassword);
		
		final UserDetails userDetails= loadUserByUsername(userName);
		
		String newGeneratedToken= jwtUtil.generateToken(userDetails);
		com.booking.jwt.entity.User user= userRepository.findByUserName(userName).get();
		
		return new JwtResponse(user, newGeneratedToken);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    com.booking.jwt.entity.User user = userRepository.findByUserName(username)
	            .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));

	    return new org.springframework.security.core.userdetails.User(
	            user.getUserName(),
	            user.getPassword(),
	            getAuthorities(user)
	    );
	}

	private Set<SimpleGrantedAuthority> getAuthorities(com.booking.jwt.entity.User user) {
	    Set<SimpleGrantedAuthority> authorities = new HashSet<>();
	    user.getRole().forEach(role -> {
//	        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
	        authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
	    });
	    return authorities;
	}

	
	private void authenticate(String userName, String userPassword) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
		} catch (DisabledException e) {
			throw new Exception("User is disabled");
		} catch (BadCredentialsException e) {
			throw new Exception("Bad Credentials from User");
		}
		
	}

}

