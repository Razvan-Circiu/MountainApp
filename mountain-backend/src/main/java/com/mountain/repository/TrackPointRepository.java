package com.mountain.repository;

import com.mountain.model.TrackPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackPointRepository extends JpaRepository<TrackPoint, Long> {
}
