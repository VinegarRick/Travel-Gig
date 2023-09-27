package com.synex.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.synex.component.BookingComponent;
import com.synex.component.EmailComponent;
import com.synex.component.HotelComponent;
import com.synex.service.UserService;

@RestController
public class GatewayController {
	
	@Autowired HotelComponent hotelComponent;
	@Autowired BookingComponent bookingComponent;
	@Autowired EmailComponent emailComponent;
	@Autowired UserService userService;
	
	@GetMapping(value="/findHotel/{searchString}")
	public JsonNode findHotel(@PathVariable String searchString) {
		return hotelComponent.findHotel(searchString);
	}
	
	@Transactional
	@PostMapping(value="/saveBooking")
	public JsonNode saveBooking(@RequestBody JsonNode payload, Principal principal) {
		JsonNode bookingData = payload.get("bookingData");
		JsonNode guestsNode = payload.get("guests");
		
		String username = principal.getName();
		String emailAddress = userService.findByUserName(username).getEmail();
				
		((ObjectNode) bookingData).put("username", username);
		
	    bookingComponent.saveGuest(guestsNode);
		JsonNode booking = bookingComponent.saveBooking(bookingData);
	    
	    emailComponent.sendEmail(emailAddress);
	    
		return booking;
	}
	
	@GetMapping(value="/findUniqueRoomTypeNamesByHotelId/{hotelId}")
	public JsonNode findUniqueRoomTypeNamesByHotelId(@PathVariable int hotelId) {
		return hotelComponent.findUniqueRoomTypeNamesByHotelId(hotelId);
	}
	
	@GetMapping(value="/findHotelRoomsByHotelId/{hotelId}")
	public JsonNode findHotelRoomsByHotelId(@PathVariable int hotelId) {
		return hotelComponent.findHotelRoomsByHotelId(hotelId);
	}
	
}
