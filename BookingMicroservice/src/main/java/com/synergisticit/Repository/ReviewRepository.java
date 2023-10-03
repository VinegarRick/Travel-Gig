package com.synergisticit.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.synergisticit.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
	
	@Query("SELECT DISTINCT r FROM Review r WHERE r.reviewId IN (SELECT b.review.reviewId FROM Booking b WHERE b.hotelId = :hotelId)")
	List<Review> findReviewsByHotelId(@Param("hotelId") int hotelId);
}
