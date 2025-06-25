package com.mountain.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String difficulty;
    private String gpxFileUrl;

    @OneToMany(mappedBy = "track", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    public Track() {}

    public Track(String name, String difficulty, String gpxFileUrl) {
        this.name = name;
        this.difficulty = difficulty;
        this.gpxFileUrl = gpxFileUrl;
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

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getGpxFileUrl() {
        return gpxFileUrl;
    }

    public void setGpxFileUrl(String gpxFileUrl) {
        this.gpxFileUrl = gpxFileUrl;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
