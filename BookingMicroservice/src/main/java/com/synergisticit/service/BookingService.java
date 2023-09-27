package com.synergisticit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergisticit.Repository.BookingRepository;
import com.synergisticit.domain.Booking;

@Service
public class BookingService {

	@Autowired BookingRepository bookingRepository;

	public Booking saveBooking(Booking booking) {
		return bookingRepository.save(booking);
	}
}
