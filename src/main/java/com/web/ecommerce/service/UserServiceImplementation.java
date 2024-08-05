package com.web.ecommerce.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.ecommerce.exception.UserException;
import com.web.ecommerce.model.User;
import com.web.ecommerce.repository.UserRepository;
import com.web.ecommerce.security.JwtHelper;

@Service
public class UserServiceImplementation implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtHelper jwtHelper;
	
	@Override
	public User findUserById(Long userId) throws UserException {
		Optional<User> user=userRepository.findById(userId);
		if(user.isPresent())
			return user.get();
		
		throw new UserException("user not found with id: "+userId);
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
	    // Trim the JWT string to remove leading and trailing whitespace
	    String trimmedJwt = jwt.trim();
	    
	    // Remove the "Bearer " prefix if it exists
	    String jwtWithoutBearer = trimmedJwt.replaceFirst("^Bearer\\s+", "");
	    
	    System.out.println(jwtWithoutBearer);
	    
	    // Get the email from the modified JWT
	    String email = jwtHelper.getUsernameFromToken(jwtWithoutBearer);
	    User user = userRepository.findByEmail(email);
	    
	    // Check if the user is found or throw an exception
	    if (user == null)
	        throw new UserException("User not found with email: " + email);
	    
	    return user;
	}


//	@Override
//	public User signupUser(User user) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
