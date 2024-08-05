package com.web.ecommerce.service;


import com.web.ecommerce.exception.UserException;
import com.web.ecommerce.model.User;

public interface UserService {

	User findUserById(Long userId) throws UserException;

	User findUserProfileByJwt(String jwt) throws UserException;
	
//	User signupUser(User user);
}
