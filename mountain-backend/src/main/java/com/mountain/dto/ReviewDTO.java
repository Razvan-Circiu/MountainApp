package com.mountain.dto;

import java.time.LocalDateTime;

public class ReviewDTO {

    private Long id;
    private String text;
    private String imageUrl;
    private LocalDateTime createdAt;
    private Long trackId;
    private String userEmail;
    private String userName;

    public ReviewDTO() {}

    public ReviewDTO(Long id, String text, String imageUrl, LocalDateTime createdAt,
                     Long trackId, String userEmail, String userName) {
        this.id = id;
        this.text = text;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.trackId = trackId;
        this.userEmail = userEmail;
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getTrackId() {
        return trackId;
    }

    public void setTrackId(Long trackId) {
        this.trackId = trackId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
