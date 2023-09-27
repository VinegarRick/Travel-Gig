package com.synergisticit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergisticit.Repository.GuestRepository;
import com.synergisticit.domain.Guest;

@Service
public class GuestService {
	
	@Autowired GuestRepository guestRepository;
	
	public void saveGuest(Guest guest) {
		guestRepository.save(guest);
	}
}
