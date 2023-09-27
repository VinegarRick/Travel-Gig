package com.synex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.synex.domain.Hotel;
import com.synex.domain.HotelRoom;

public interface HotelRepository extends JpaRepository<Hotel, Integer>{
	public List<Hotel> findByHotelNameLikeOrCityLikeOrStateLike(String hotelName, String city, String state);
	
    @Query("SELECT DISTINCT hr.type.name FROM Hotel h JOIN h.hotelRooms hr WHERE h.hotelId = :hotelId")
    public List<String> findUniqueRoomTypeNamesByHotelId(int hotelId);
    
    @Query("SELECT hr FROM Hotel h JOIN h.hotelRooms hr WHERE h.hotelId = :hotelId")
    public List<HotelRoom> findHotelRoomsByHotelId(int hotelId);
}
