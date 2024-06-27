package com.hoa.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hoa.exception.ProductException;
import com.hoa.model.Product;
import com.hoa.model.Rating;
import com.hoa.model.User;
import com.hoa.repository.RatingRepository;
import com.hoa.request.RatingRequest;

@Service
public class RatingServiceImplementation implements RatingService{

	private RatingRepository ratingRepository;
	private ProductService productService;
	
	public RatingServiceImplementation(RatingRepository ratingRepository,
			ProductService productService) {
		this.productService = productService;
		this.ratingRepository= ratingRepository;
	}
	
	@Override
	public Rating createRating(RatingRequest req, User user) throws ProductException {

		Product product = productService.findProductById(req.getProductId());
		
		Rating rating = new Rating();
		rating.setProduct(product);
		rating.setUser(user);
		rating.setRating(req.getRating());
		rating.setCreatedAt(LocalDateTime.now());
		
		return ratingRepository.save(rating);
	}

	@Override
	public List<Rating> getProductRating(Long productId) {
		return ratingRepository.getAllProductsRating(productId);
	}

	
}
