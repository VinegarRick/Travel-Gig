package com.synergisticit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergisticit.Repository.ReviewRepository;
import com.synergisticit.domain.Review;

@Service
public class ReviewService {

	@Autowired ReviewRepository reviewRepository;
	
	public Review saveReview(Review review) {
		return reviewRepository.save(review);
	}
	
	public List<Review> findReviewsByHotelId(int hotelId) {
		return reviewRepository.findReviewsByHotelId(hotelId);
	}
	
}
