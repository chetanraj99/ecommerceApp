package com.web.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.ecommerce.exception.UserException;
import com.web.ecommerce.model.Cart;
import com.web.ecommerce.model.JwtResponse;
import com.web.ecommerce.model.User;
import com.web.ecommerce.repository.UserRepository;
import com.web.ecommerce.security.JwtHelper;
import com.web.ecommerce.service.CartService;
@RestController
@RequestMapping("/home")
public class HomeController {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
//	@PostMapping("/signup")
//	public User signupUser(@RequestBody User user) {
//		return this.userService.signupUser(user);
//	}
	
	@Autowired
    private UserDetailsService userDetailsService;
	
	@Autowired
	private CartService cartService;
    

    @Autowired
    private JwtHelper helper;
    
    @GetMapping("/admin")
    public ResponseEntity<String> adminUser(){
		return ResponseEntity.ok("Yes, I am Admin user");
	}


	@PostMapping("/signup")
	public ResponseEntity<JwtResponse> createUserHandler(@RequestBody User user) throws UserException{
		System.out.println("hello");
		String email=user.getEmail();
		String password=user.getPassword();
		
		String firstName=user.getFisrtName();
		String lastName=user.getLastName();
		
		User isEmailExist=userRepository.findByEmail(email);
		
		if(isEmailExist!=null) {
			throw new UserException("Email is already used with another account");
		}
		
		User createdUser=new User();
		createdUser.setEmail(email);
		createdUser.setPassword(passwordEncoder.encode(password));
		createdUser.setFisrtName(firstName);
		createdUser.setLastName(lastName);
		
		User savedUser=userRepository.save(createdUser);
		Cart cart=cartService.createCart(savedUser);
		
		Authentication authentication=new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(savedUser.getEmail());
        String token = this.helper.generateToken(userDetails);
//		String token=this.helper.generateToekn(authentication);
		
		JwtResponse jwtResponse=new JwtResponse(token,"Signup Success");
		
		return new ResponseEntity<JwtResponse>(jwtResponse,HttpStatus.CREATED);
	}
}
