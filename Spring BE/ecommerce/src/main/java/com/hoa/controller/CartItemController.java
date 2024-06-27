package com.hoa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoa.exception.CartItemException;
import com.hoa.exception.UserException;
import com.hoa.model.CartItem;
import com.hoa.model.User;
import com.hoa.response.ApiResponse;
import com.hoa.service.CartItemService;
import com.hoa.service.CartService;
import com.hoa.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/cart")
public class CartItemController {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartItemService cartItemService;
	
	@DeleteMapping("/item_delete/{cartItemId}")
    @Operation(description = "Remove item from cart")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable Long cartItemId,
    		@RequestHeader("Authorization") String jwt) throws UserException, CartItemException {
        User user = userService.findUserProfileByJwt(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);
        
        ApiResponse res = new ApiResponse();
        res.setMessage("Item removed from cart");
        res.setStatus(true);
        
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
	
	@PutMapping("/update/{cartItemId}")
    @Operation(description = "Update item in cart")
    public ResponseEntity<CartItem> updateCartItem(@RequestBody CartItem cartItem, @PathVariable Long cartItemId,
    		@RequestHeader("Authorization") String jwt) throws UserException, CartItemException {
        User user = userService.findUserProfileByJwt(jwt);
        
        CartItem updatedCartItem = cartItemService.upadteCartItem(user.getId(), cartItemId, cartItem);
        
        return new ResponseEntity<>(updatedCartItem, HttpStatus.OK);
    }
}
