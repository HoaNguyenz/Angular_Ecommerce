package com.hoa.service;

import org.springframework.stereotype.Service;

import com.hoa.exception.ProductException;
import com.hoa.model.Cart;
import com.hoa.model.CartItem;
import com.hoa.model.Product;
import com.hoa.model.User;
import com.hoa.repository.CartRepository;
import com.hoa.request.AddItemRequest;

@Service
public class CartServiceImplementation implements CartService{

	private CartRepository cartRepository;
	private CartItemService cartItemService;
	private ProductService productService;
	
	public CartServiceImplementation(CartRepository cartRepository,
			CartItemService cartItemService,
			ProductService productService) {
		this.cartItemService = cartItemService;
		this.cartRepository = cartRepository;
		this.productService = productService;
	}
	
	@Override
	public Cart createCart(User user) {
		Cart cart = new Cart();
		cart.setUser(user);
		return cartRepository.save(cart);
	}

	@Override
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
		
		Cart cart = cartRepository.findByUserId(userId);
		Product product = productService.findProductById(req.getProductId());
		
		CartItem isPresent = cartItemService.isCartItemExist(cart, product, req.getSize(), userId);
		
		if(isPresent == null) {
			CartItem cartItem = new CartItem();
			cartItem.setProduct(product);
			cartItem.setCart(cart);
			cartItem.setQuantity(req.getQuantity());
			cartItem.setUserId(userId);
			
			int price = req.getQuantity()*product.getPrice();
			cartItem.setPrice(price);
			cartItem.setSize(req.getSize());
		
			CartItem createdCartItem = cartItemService.createCartItem(cartItem);
			cart.getCartItems().add(createdCartItem);
		}
		return "Item added to cart";
	}

	@Override
	public Cart findUserCart(Long userId) {
		Cart cart = cartRepository.findByUserId(userId);
		
		int totalPrice = 0;
		int totalItem = 0;
		
		for(CartItem cartItem :cart.getCartItems()) {
			totalPrice = totalPrice + cartItem.getPrice();
			totalItem = totalItem + cartItem.getQuantity();
		}
		
		cart.setTotalPrice(totalPrice);
		cart.setTotalItem(totalItem);
		
		return cartRepository.save(cart);
	}

	
	
}
