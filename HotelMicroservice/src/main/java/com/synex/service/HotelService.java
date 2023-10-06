package com.synex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synex.domain.Hotel;
import com.synex.domain.HotelRoom;
import com.synex.repository.HotelRepository;

@Service
public class HotelService {

	@Autowired
	HotelRepository hotelRepository;
	
	public List<Hotel> findHotel(String searchString) {
		searchString = "%" + searchString + "%";
		
		List<Hotel> hotels = hotelRepository.findByHotelNameLikeOrCityLikeOrStateLike(searchString, searchString, searchString);
				
		/*for (Hotel hotel : hotels) {
			System.out.println(hotel.getAmenities().size());
		}*/
		
		return hotels;
		//return hotelRepository.findByHotelNameLikeOrCityLikeOrStateLike(searchString, searchString, searchString);
	}
	
	public List<String> findUniqueRoomTypeNamesByHotelId(int hotelId) {
		return hotelRepository.findUniqueRoomTypeNamesByHotelId(hotelId);
	}
	
	public List<HotelRoom> findHotelRoomsByHotelId(int hotelId) {
		return hotelRepository.findHotelRoomsByHotelId(hotelId);
	}
}
