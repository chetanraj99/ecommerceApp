package com.web.ecommerce.controller;

import com.web.ecommerce.exception.CartItemException;
import com.web.ecommerce.exception.UserException;
import com.web.ecommerce.model.Cart;
import com.web.ecommerce.model.CartItem;
import com.web.ecommerce.model.Product;
import com.web.ecommerce.model.User;
import com.web.ecommerce.service.CartItemService;
import com.web.ecommerce.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart_items")
public class CartItemController {

    private final CartItemService cartItemService;
    
    @Autowired
    private UserService userService;

    @Autowired
    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping("/create")
    public ResponseEntity<CartItem> createCartItem(@RequestBody CartItem cartItem) {
        CartItem createdCartItem = cartItemService.createCartItem(cartItem);
        return ResponseEntity.ok(createdCartItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartItem> updateCartItem(@RequestHeader("Authorization") String jwt, @PathVariable Long id, @RequestBody CartItem cartItem) {
        try {
        	User user=userService.findUserProfileByJwt(jwt);
            CartItem updatedCartItem = cartItemService.updateCartItem(user.getId(), id, cartItem);
            return ResponseEntity.ok(updatedCartItem);
        } catch (CartItemException | UserException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{cart}/{product}/{size}/{userId}")
    public ResponseEntity<CartItem> isCartItemExist(@PathVariable Cart cart, @PathVariable Product product, @PathVariable String size, @PathVariable Long userId) {
        CartItem existingCartItem = cartItemService.isCartItemExist(cart, product, size, userId);
        if (existingCartItem != null) {
            return ResponseEntity.ok(existingCartItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse> removeCartItem(@PathVariable Long cartItemId,
    		@RequestHeader("Authorization") String jwt) throws UserException,CartItemException {
        
    	User user=userService.findUserProfileByJwt(jwt);
    	cartItemService.removeCartItem(user.getId(), cartItemId);
    	
    	ApiResponse res=new ApiResponse();
    	res.setMessage("item deleted to cart");
    	res.setStatus(true);
    	return new ResponseEntity<ApiResponse>(res,HttpStatus.OK);
    	
    }

    @GetMapping("/{cartItemId}")
    public ResponseEntity<CartItem> findCartItemById(@PathVariable Long cartItemId) {
        try {
            CartItem cartItem = cartItemService.findCartItemById(cartItemId);
            return ResponseEntity.ok(cartItem);
        } catch (CartItemException | UserException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
