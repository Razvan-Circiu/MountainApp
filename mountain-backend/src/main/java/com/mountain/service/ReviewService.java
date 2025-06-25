package com.mountain.service;

import com.mountain.dto.ReviewDTO;
import com.mountain.model.Review;
import com.mountain.model.Track;
import com.mountain.model.User;
import com.mountain.repository.ReviewRepository;
import com.mountain.repository.TrackRepository;
import com.mountain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private UserRepository userRepository;

    public List<ReviewDTO> getReviewsByTrackId(Long trackId) {
        Optional<Track> optionalTrack = trackRepository.findById(trackId);
        if (optionalTrack.isEmpty()) return List.of();

        List<Review> reviews = reviewRepository.findByTrack(optionalTrack.get());

        return reviews.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ReviewDTO addReview(ReviewDTO reviewDTO) {
        Track track = trackRepository.findById(reviewDTO.getTrackId())
                .orElseThrow(() -> new IllegalArgumentException("Track not found"));

        User user = userRepository.findByEmail(reviewDTO.getUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Review review = new Review();
        review.setText(reviewDTO.getText());
        review.setImageUrl(reviewDTO.getImageUrl());
        review.setCreatedAt(LocalDateTime.now());
        review.setTrack(track);
        review.setUser(user);

        Review saved = reviewRepository.save(review);
        return convertToDto(saved);
    }

    private ReviewDTO convertToDto(Review review) {
        return new ReviewDTO(
                review.getId(),
                review.getText(),
                review.getImageUrl(),
                review.getCreatedAt(),
                review.getTrack().getId(),
                review.getUser().getEmail(),
                review.getUser().getName()
        );
    }
}
