package com.synergisticit.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.synergisticit.domain.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

}
