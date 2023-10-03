package com.synergisticit.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.synergisticit.domain.Booking;
import com.synergisticit.domain.Guest;
import com.synergisticit.domain.Review;
import com.synergisticit.service.BookingService;
import com.synergisticit.service.GuestService;
import com.synergisticit.service.ReviewService;

@RestController
public class BookingController {
	
	@Autowired BookingService bookingService;
	@Autowired GuestService guestService;
	@Autowired ReviewService reviewService;
	
	@PostMapping(value="/saveBooking")
	public Booking saveBooking(@RequestBody Booking booking) {
		System.out.println("saving booking...");
		
		return bookingService.saveBooking(booking);
	}
	
	@PostMapping(value="/saveGuest")
	//public void saveGuest(@RequestBody Guest guest) {
	public void saveGuest(@RequestBody List<Guest> guests) {
		System.out.println("saving guest...");
		
		for (Guest guest : guests) {
			guestService.saveGuest(guest);
		}
	}	
	
	@GetMapping(value="/findAllBookingsByUsername/{username}")
	public List<Booking> findAllBookingsByUsername(@PathVariable String username) {
		System.out.println("retrieving all bookings by username: " + username + "...");
		
		return bookingService.findAllBookingsByUsername(username);
	}
	
	@GetMapping(value="/findReviewsByHotelId/{hotelId}")
	public List<Review> findReviewsByHotelId(@PathVariable int hotelId) {
		return reviewService.findReviewsByHotelId(hotelId);
	}
	
	@GetMapping(value="/findBookingByReviewId/{reviewId}")
	public Booking findBookingByReviewId(@PathVariable int reviewId) {
		return bookingService.findBookingByReviewId(reviewId);
	}
	
	@PostMapping(value="/updateBookingStatus")
	public Booking updateBookingStatus(@RequestBody Map<String, Object> requestBody) {
		System.out.println("updating booking status...");
		
		int bookingId = (int)requestBody.get("bookingId");
		String status = (String)requestBody.get("status");
		
		Booking existingBooking = bookingService.findById(bookingId);
		if (existingBooking != null) {
			existingBooking.setStatus(status);
			return bookingService.saveBooking(existingBooking);
		} else {
			System.out.println("No booking exists with id: " + bookingId);
			return null;
		}
	}
	
	@PostMapping(value="/saveReview")
	//public Review saveReview(@RequestBody Review review) {
	public Review saveReview(@RequestBody Map<String, Object> requestBody) {
		System.out.println("saving review...");
		
	    Map<String, Object> reviewMap = (Map<String, Object>) requestBody.get("review");
	    ObjectMapper objectMapper = new ObjectMapper();
	    Review review = objectMapper.convertValue(reviewMap, Review.class);
		
		//Review review = (Review)requestBody.get("review");
		int bookingId = (int)requestBody.get("bookingId");
		
		Review myReview = reviewService.saveReview(review);
		
		Booking existingBooking = bookingService.findById(bookingId);
		if (existingBooking != null) {
			existingBooking.setReview(myReview);
			bookingService.saveBooking(existingBooking);
		} else {
			System.out.println("No booking exists with id: " + bookingId);
			return null;
		}
				
		return myReview;
	}
}
