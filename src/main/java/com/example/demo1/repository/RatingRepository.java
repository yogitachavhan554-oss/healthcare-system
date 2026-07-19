package com.example.demo1.repository;

import  com.example.demo1.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
	
	@Query("SELECT AVG(r.score) FROM Rating r WHERE r.caregiver.id = :caregiverId")
	Double getAverageRating(@Param("caregiverId") Long caregiverId);
}