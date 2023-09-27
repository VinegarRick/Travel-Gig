package com.synex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.synex.domain.Hotel;
import com.synex.domain.HotelRoom;
import com.synex.service.HotelService;

@RestController
public class HotelController {
	
	@Autowired
	HotelService hotelService;
	
	@GetMapping(value="/searchHotel/{searchString}")
	public List<Hotel> searchHotel(@PathVariable String searchString) {
		return hotelService.findHotel(searchString);
	}
	
	@GetMapping(value="/findUniqueRoomTypeNamesByHotelId/{hotelId}")
	public List<String> findUniqueRoomTypeNamesByHotelId(@PathVariable int hotelId) {
		return hotelService.findUniqueRoomTypeNamesByHotelId(hotelId);
	}
	
	@GetMapping(value="/findHotelRoomsByHotelId/{hotelId}")
	public List<HotelRoom> findHotelRoomsByHotelId(@PathVariable int hotelId) {
		return hotelService.findHotelRoomsByHotelId(hotelId);
	}
}
