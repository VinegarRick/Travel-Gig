package com.synergisticit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.synergisticit.domain.Booking;
import com.synergisticit.domain.Guest;
import com.synergisticit.service.BookingService;
import com.synergisticit.service.GuestService;

@RestController
public class BookingController {
	
	@Autowired BookingService bookingService;
	@Autowired GuestService guestService;
	
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
}
