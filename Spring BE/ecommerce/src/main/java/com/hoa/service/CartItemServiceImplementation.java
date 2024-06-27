package com.hoa.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hoa.exception.CartItemException;
import com.hoa.exception.UserException;
import com.hoa.model.Cart;
import com.hoa.model.CartItem;
import com.hoa.model.Product;
import com.hoa.model.User;
import com.hoa.repository.CartItemRepository;
import com.hoa.repository.CartRepository;

@Service
public class CartItemServiceImplementation implements CartItemService{

	private CartItemRepository cartItemRepository;
	private UserService userService;
	private CartRepository cartRepository;
	
	public CartItemServiceImplementation(CartItemRepository cartItemRepository,
			UserService userService,
			CartRepository cartRepository) {
		this.cartItemRepository = cartItemRepository;
		this.cartRepository = cartRepository;
		this.userService = userService;
	}
	
	
	@Override
	public CartItem createCartItem(CartItem cartItem) {
		cartItem.setQuantity(1);
		cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
		
		CartItem createdCartItem = cartItemRepository.save(cartItem);
		return createdCartItem;
	}

	@Override
	public CartItem upadteCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {
		
		CartItem item = findCartItemById(id);
		User user = userService.findUserbyId(item.getUserId());
		
		if(user.getId().equals(userId)) {
			item.setQuantity(cartItem.getQuantity());
			item.setPrice(item.getQuantity()*item.getProduct().getPrice());
			
		}
		return cartItemRepository.save(item);
	}

	@Override
	public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
		
		CartItem cartItem = cartItemRepository.isCartItemExist(cart, product, size, userId);
			
		return cartItem;
	}

	@Override
	public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
		CartItem cartItem = findCartItemById(cartItemId);
		
		User user = userService.findUserbyId(cartItem.getUserId());
		User reqUser = userService.findUserbyId(userId);
		
		if(user.getId().equals(reqUser.getId())) {
			cartItemRepository.deleteById(cartItemId);
		}
		else {
			throw new UserException("You cant remove another users item");
		}
	}

	@Override
	public CartItem findCartItemById(Long cartItemId) throws CartItemException {
		Optional<CartItem> opt = cartItemRepository.findById(cartItemId);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		
		throw new CartItemException("Cart item not found with id - " + cartItemId);
	}

	
}
