package com.synergisticit.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.synergisticit.domain.Guest;

public interface GuestRepository extends JpaRepository<Guest, Integer> {

}
