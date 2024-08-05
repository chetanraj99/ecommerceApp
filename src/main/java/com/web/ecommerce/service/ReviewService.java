package com.web.ecommerce.service;

import java.util.List;

import com.web.ecommerce.exception.ProductException;
import com.web.ecommerce.model.Review;
import com.web.ecommerce.model.User;


public interface ReviewService {

	public Review createReview(ReviewRequest req,User user) throws ProductException;
	public List<Review> getAllReview(Long productId);
}
