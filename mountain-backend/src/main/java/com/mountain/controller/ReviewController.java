package com.mountain.controller;

import com.mountain.dto.ReviewDTO;
import com.mountain.model.Review;
import com.mountain.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/track/{trackId}")
    public ResponseEntity<List<Review>> getReviewsForTrack(@PathVariable Long trackId) {
        return ResponseEntity.ok(reviewService.getReviewsByTrackId(trackId));
    }

    @PostMapping
    public ResponseEntity<String> addReview(@RequestBody ReviewDTO dto, Principal principal) {
        Review saved = reviewService.addReview(dto, principal);
        if (saved == null) return ResponseEntity.badRequest().body("Invalid track or user.");
        return ResponseEntity.ok("Review added successfully");
    }
}
