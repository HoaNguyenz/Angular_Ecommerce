package com.hoa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoa.exception.ProductException;
import com.hoa.exception.UserException;
import com.hoa.model.Rating;
import com.hoa.model.User;
import com.hoa.request.RatingRequest;
import com.hoa.service.RatingService;
import com.hoa.service.UserService;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RatingService ratingService;
	
	@PostMapping("/create")
	public ResponseEntity<Rating> createRating(@RequestBody RatingRequest req,
			@RequestHeader("Authorization") String jwt) throws UserException, ProductException{
		User user = userService.findUserProfileByJwt(jwt);
		Rating rating = ratingService.createRating(req, user);
		
		return new ResponseEntity<Rating>(rating, HttpStatus.CREATED);
	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Rating>> getProductsRating(@PathVariable Long productId)
			throws UserException, ProductException{
		List<Rating> ratings = ratingService.getProductRating(productId);
		
		return new ResponseEntity<>(ratings, HttpStatus.CREATED);
	}
}
