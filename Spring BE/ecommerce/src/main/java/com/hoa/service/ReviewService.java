package com.hoa.service;

import java.util.List;

import com.hoa.exception.ProductException;
import com.hoa.model.Review;
import com.hoa.model.User;
import com.hoa.request.ReviewRequest;

public interface ReviewService {

	public Review createReview(ReviewRequest req, User user) throws ProductException;
	public List<Review> getAllReview(Long productId);
	
}
