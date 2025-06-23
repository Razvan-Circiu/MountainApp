package com.mountain.repository;

import com.mountain.model.Review;
import com.mountain.model.Trail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByTrail(Trail trail);
}
