package com.web.ecommerce.controller;

import com.web.ecommerce.exception.ProductException;
import com.web.ecommerce.exception.UserException;
import com.web.ecommerce.model.Review;
import com.web.ecommerce.model.User;
import com.web.ecommerce.service.ReviewRequest;
import com.web.ecommerce.service.ReviewService;
import com.web.ecommerce.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
	
	@Autowired
    private  ReviewService reviewService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Review> createReview(@RequestBody ReviewRequest req,
    		@RequestHeader("Authorization") String jwt) throws UserException ,ProductException {
        
    	User user=userService.findUserProfileByJwt(jwt);
    	
    	Review review=reviewService.createReview(req, user);
    	
    	return new ResponseEntity<Review>(review,HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getAllReview(@PathVariable Long productId)
    throws UserException, ProductException{
    	
        List<Review> reviews = reviewService.getAllReview(productId);
        
        return new ResponseEntity<List<Review>>(reviews,HttpStatus.CREATED);
    }
}
