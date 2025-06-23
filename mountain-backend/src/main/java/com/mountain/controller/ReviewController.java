package com.mountain.controller;

import com.mountain.model.Review;
import com.mountain.model.Trail;
import com.mountain.repository.ReviewRepository;
import com.mountain.repository.TrailRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trails/{trailId}/reviews")
@CrossOrigin
public class ReviewController {

    private final ReviewRepository reviewRepo;
    private final TrailRepository trailRepo;

    public ReviewController(ReviewRepository reviewRepo, TrailRepository trailRepo) {
        this.reviewRepo = reviewRepo;
        this.trailRepo = trailRepo;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getReviews(@PathVariable Long trailId) {
        return trailRepo.findById(trailId)
                .map(trail -> ResponseEntity.ok(reviewRepo.findByTrail(trail)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Review> addReview(
            @PathVariable Long trailId,
            @RequestBody Review reviewData) {

        return trailRepo.findById(trailId)
                .map(trail -> {
                    reviewData.setTrail(trail);
                    return ResponseEntity.ok(reviewRepo.save(reviewData));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
