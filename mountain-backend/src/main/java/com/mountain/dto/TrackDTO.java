package com.mountain.dto;

import java.util.List;

public class TrackDTO {
    private String name;
    private String description;
    private List<TrackPointDTO> trackPoints;

    // Getters & Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public List<TrackPointDTO> getTrackPoints() {
        return trackPoints;
    }
    public void setTrackPoints(List<TrackPointDTO> trackPoints) {
        this.trackPoints = trackPoints;
    }
}
