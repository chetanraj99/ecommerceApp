package com.web.ecommerce.service;

import com.web.ecommerce.exception.ProductException;
import com.web.ecommerce.model.Cart;
import com.web.ecommerce.model.User;

public interface CartService {

	public Cart createCart(User user);
	
	public String addCartItem(Long userId,AddItemRequest req) throws ProductException;
	
	public Cart findUserCart(Long cartId);
	
	
}
