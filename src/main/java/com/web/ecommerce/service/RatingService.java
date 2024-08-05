package com.web.ecommerce.service;

import java.util.List;

import com.web.ecommerce.exception.ProductException;
import com.web.ecommerce.model.Rating;
import com.web.ecommerce.model.User;

public interface RatingService {

	public Rating createRating(RatingRequest req,User user) throws ProductException;
	
	public List<Rating> getProductsRating(Long productId);
}
