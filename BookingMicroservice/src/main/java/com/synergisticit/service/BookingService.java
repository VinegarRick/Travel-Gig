package com.synergisticit.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.el.stream.Optional;
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
	
	public List<Booking> findAllBookingsByUsername(String username) {
		List<Booking> bookings = new ArrayList<>();
		Iterable<Booking> iterable = bookingRepository.findByUserName(username);
		Iterator<Booking> itr = iterable.iterator();
		
		while (itr.hasNext()) {
			bookings.add(itr.next());
		}
		
		return bookings;
	}
	
	public Booking findById(int bookingId) {
		return bookingRepository.findById(bookingId).orElse(null);
	}
}
