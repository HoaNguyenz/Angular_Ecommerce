package com.hoa.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hoa.exception.ProductException;
import com.hoa.model.Product;
import com.hoa.model.Review;
import com.hoa.model.User;
import com.hoa.repository.ProductRepository;
import com.hoa.repository.ReviewRepository;
import com.hoa.request.ReviewRequest;

@Service
public class ReviewServiceImplementation implements ReviewService{

	private ReviewRepository reviewRepository;
	private ProductService productService;
	private ProductRepository productRepository;
	
	ReviewServiceImplementation(ReviewRepository reviewRepository,
			ProductService productService,
			ProductRepository productRepository){
		this.productRepository = productRepository;
		this.productService = productService;
		this.reviewRepository = reviewRepository;
		
	}
	
	@Override
	public Review createReview(ReviewRequest req, User user) throws ProductException {
		Product product = productService.findProductById(req.getProductId());
		
		Review review = new Review();
		review.setUser(user);
		review.setProduct(product);
		review.setReview(req.getReview());
		review.setCreatedAt(LocalDateTime.now());
		
		return reviewRepository.save(review);
	}

	@Override
	public List<Review> getAllReview(Long productId) {	
		return reviewRepository.getAllProductsReview(productId);
	}

	
	
}
