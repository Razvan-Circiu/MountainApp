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

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Review> getReviewsByTrackId(Long trackId) {
        return reviewRepository.findByTrackId(trackId);
    }

    public Review addReview(ReviewDTO dto, Principal principal) {
        Optional<Track> trackOpt = trackRepository.findById(dto.getTrackId());
        if (trackOpt.isEmpty()) return null;

        User user = userRepository.findByEmail(principal.getName());

        Review review = new Review();
        review.setText(dto.getText());
        review.setImageUrl(dto.getImageUrl());
        review.setTrack(trackOpt.get());
        review.setUser(user);

        return reviewRepository.save(review);
    }
}
