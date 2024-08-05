package com.web.ecommerce.controller;

import com.web.ecommerce.exception.ProductException;
import com.web.ecommerce.exception.UserException;
import com.web.ecommerce.model.Rating;
import com.web.ecommerce.model.User;
import com.web.ecommerce.service.RatingRequest;
import com.web.ecommerce.service.RatingService;
import com.web.ecommerce.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {
	
	@Autowired
    private RatingService ratingService;
	
	@Autowired
	private UserService userService;


    @PostMapping("/create")
    public ResponseEntity<Rating> createRating(@RequestBody RatingRequest req,
    		@RequestHeader("Authorization") String jwt) throws UserException ,ProductException{
        
            User user =userService.findUserProfileByJwt(jwt); 
            Rating rating = ratingService.createRating(req, user);
            
            return new ResponseEntity<Rating>(rating,HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Rating>> getProductsRating(@PathVariable Long productId,
    		@RequestHeader("Authorization") String jwt) throws UserException ,ProductException {
        
    	User user=userService.findUserProfileByJwt(jwt);
    	List<Rating> ratings = ratingService.getProductsRating(productId);
    	
        return ResponseEntity.ok(ratings);
    }
}
