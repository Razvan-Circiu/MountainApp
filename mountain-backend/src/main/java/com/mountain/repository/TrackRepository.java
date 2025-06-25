package com.mountain.repository;

import com.mountain.model.Track;
import com.mountain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrackRepository extends JpaRepository<Track, Long> {
    List<Track> findByUser(User user);
}
