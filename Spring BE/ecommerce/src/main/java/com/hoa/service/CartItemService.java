package com.hoa.service;

import com.hoa.exception.CartItemException;
import com.hoa.exception.UserException;
import com.hoa.model.Cart;
import com.hoa.model.CartItem;
import com.hoa.model.Product;

public interface CartItemService {
	public CartItem createCartItem(CartItem cartItem);
	
	public CartItem upadteCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException;

	public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);

	public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException;
	
	public CartItem findCartItemById(Long cartItemId) throws CartItemException;
}
