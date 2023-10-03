package com.synergisticit.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.synergisticit.domain.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
	
	List<Booking> findByUserName(String username);
	
	@Query("SELECT b FROM Booking b WHERE b.review.reviewId = :reviewId")
	Booking findByReviewId(int reviewId);
}
