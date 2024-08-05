
package com.web.ecommerce.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.ecommerce.exception.ProductException;
import com.web.ecommerce.exception.UserException;
import com.web.ecommerce.model.Cart;
import com.web.ecommerce.model.User;
import com.web.ecommerce.service.AddItemRequest;
import com.web.ecommerce.service.CartService;
import com.web.ecommerce.service.UserService;


@RestController
@RequestMapping("/api/cart")
public class CartController {
    
    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);  // Fixed typo and added assignment
        Cart cart = cartService.findUserCart(user.getId());
        return new ResponseEntity<Cart>(cart,HttpStatus.OK);
    }
    
    @PutMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest req,
    		@RequestHeader("Authorization") String jwt) throws UserException,ProductException{
    	User user=userService.findUserProfileByJwt(jwt);
    	
    	cartService.addCartItem(user.getId(), req);
    	
    	ApiResponse res=new ApiResponse();
    	res.setMessage("item added to cart");
    	res.setStatus(true);
    	return new ResponseEntity<ApiResponse>(res,HttpStatus.OK);
    	
    }
    
    
}

