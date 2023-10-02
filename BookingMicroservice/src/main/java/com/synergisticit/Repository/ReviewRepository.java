package com.synergisticit.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.synergisticit.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

}
