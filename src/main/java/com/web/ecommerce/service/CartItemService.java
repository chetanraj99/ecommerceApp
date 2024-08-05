package com.web.ecommerce.service;

import com.web.ecommerce.exception.CartItemException;
import com.web.ecommerce.exception.UserException;
import com.web.ecommerce.model.Cart;
import com.web.ecommerce.model.CartItem;
import com.web.ecommerce.model.Product;

public interface CartItemService {

	public CartItem createCartItem(CartItem cartItem);
	
	public CartItem updateCartItem(Long userId, Long id,CartItem cartItem) throws CartItemException,UserException;
	
	public CartItem isCartItemExist(Cart cart,Product product,String size,Long userId);
	
	public void removeCartItem(Long userId,Long cartItemId) throws CartItemException,UserException;
	
	public CartItem findCartItemById(Long cartItemId) throws CartItemException,UserException;
}
