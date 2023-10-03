package com.synergisticit.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.synergisticit.domain.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
	
	List<Booking> findByUserName(String username);
}
