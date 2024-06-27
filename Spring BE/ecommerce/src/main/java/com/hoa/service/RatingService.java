package com.hoa.service;

import java.util.List;

import com.hoa.exception.ProductException;
import com.hoa.model.Rating;
import com.hoa.model.User;
import com.hoa.request.RatingRequest;

public interface RatingService {
	
	public Rating createRating(RatingRequest req, User user) throws ProductException;

	public List<Rating> getProductRating(Long productId);


}
