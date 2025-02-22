package com.hoa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hoa.exception.CartItemException;
import com.hoa.exception.ProductException;
import com.hoa.exception.UserException;
import com.hoa.model.Cart;
import com.hoa.model.CartItem;
import com.hoa.model.User;
import com.hoa.request.AddItemRequest;
import com.hoa.response.ApiResponse;
import com.hoa.service.CartItemService;
import com.hoa.service.CartService;
import com.hoa.service.UserService;

@RestController
@RequestMapping("/api/cart")
@Tag(name = "Cart Management", description = "find user cart, add item to cart")
public class CartController {
	@Autowired
	private CartService cartService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartItemService cartItemService;
	
	@GetMapping("/")
	@Operation(description = "find cart by user id")
	public ResponseEntity<Cart> findUserByCart(@RequestHeader("Authorization") String jwt) throws UserException{
		User user = userService.findUserProfileByJwt(jwt);
		Cart cart = cartService.findUserCart(user.getId());
		
		return new ResponseEntity<>(cart, HttpStatus.OK);
	}
	
	@PutMapping("/add")
	@Operation(description = "add item to cart")
	public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest req,
			@RequestHeader("Authorization") String jwt) throws UserException, ProductException{
		User user = userService.findUserProfileByJwt(jwt);
		cartService.addCartItem(user.getId(), req);
		
		ApiResponse res = new ApiResponse();
		res.setMessage("item added to cart");
		res.setStatus(true);
		
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
//	@DeleteMapping("/item_delete/{cartItemId}")
//    @Operation(description = "Remove item from cart")
//    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable Long cartItemId,
//    		@RequestHeader("Authorization") String jwt) throws UserException, CartItemException {
//        User user = userService.findUserProfileByJwt(jwt);
//        cartItemService.removeCartItem(user.getId(), cartItemId);
//        ApiResponse res = new ApiResponse();
//        res.setMessage("Item removed from cart");
//        res.setStatus(true);
//        
//        return new ResponseEntity<>(res, HttpStatus.OK);
//    }
	
}
