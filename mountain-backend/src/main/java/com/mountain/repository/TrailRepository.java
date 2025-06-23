package com.mountain.repository;

import com.mountain.model.Trail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrailRepository extends JpaRepository<Trail, Long> {
}
