package com.mountain.model;

import jakarta.persistence.*;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String user;

    private String text;

    private int rating;

    @ManyToOne
    @JoinColumn(name = "trail_id")
    private Trail trail;

    public Review() {}

    public Review(String user, String text, int rating, Trail trail) {
        this.user = user;
        this.text = text;
        this.rating = rating;
        this.trail = trail;
    }

    public Long getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Trail getTrail() {
        return trail;
    }

    public void setTrail(Trail trail) {
        this.trail = trail;
    }
}
