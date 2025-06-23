package com.mountain.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Trail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String imagePath;

    @ElementCollection
    @CollectionTable(name = "trail_points", joinColumns = @JoinColumn(name = "trail_id"))
    private List<Coordinate> points;

    public Trail() {}

    public Trail(String name, List<Coordinate> points, String imagePath) {
        this.name = name;
        this.points = points;
        this.imagePath = imagePath;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Coordinate> getPoints() {
        return points;
    }

    public void setPoints(List<Coordinate> points) {
        this.points = points;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
