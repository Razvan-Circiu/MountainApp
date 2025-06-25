package com.mountain.controller;

import com.exemplu.mountain.dto.ReviewDTO;
import com.exemplu.mountain.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/track/{trackId}")
    public List<ReviewDTO> getReviewsByTrack(@PathVariable Long trackId) {
        return reviewService.getReviewsByTrackId(trackId);
    }

    @PostMapping
    public ReviewDTO submitReview(@RequestBody ReviewDTO reviewDTO) {
        return reviewService.addReview(reviewDTO);
    }
}
