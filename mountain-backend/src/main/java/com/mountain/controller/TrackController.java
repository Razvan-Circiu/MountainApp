package com.mountain.controller;

import com.mountain.model.Track;
import com.mountain.model.TrackPoint;
import com.mountain.repository.TrackRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tracks")
@CrossOrigin
public class TrackController {

    private final TrackRepository trackRepository;

    public TrackController(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @PostMapping
    public ResponseEntity<?> saveTrack(@RequestBody TrackRequest request, Authentication auth) {
        Track track = new Track();
        track.setName(request.getName());
        track.setUsername(auth.getName()); // username extras din token JWT

        List<TrackPoint> trackPoints = request.getPoints().stream().map(p -> {
            TrackPoint tp = new TrackPoint();
            tp.setLatitude(p.getLatitude());
            tp.setLongitude(p.getLongitude());
            tp.setTimestamp(p.getTimestamp());
            return tp;
        }).toList();

        track.setPoints(trackPoints);
        trackRepository.save(track);

        return ResponseEntity.ok("Traseu salvat cu succes.");
    }

    @GetMapping
    public ResponseEntity<List<Track>> getTracks(Authentication auth) {
        List<Track> tracks = trackRepository.findByUsername(auth.getName());
        return ResponseEntity.ok(tracks);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTrack(@PathVariable Long id, Authentication auth) {
        return trackRepository.findById(id)
                .map(track -> {
                    if (!track.getUsername().equals(auth.getName())) {
                        return ResponseEntity.status(403).body("Acces interzis.");
                    }
                    trackRepository.delete(track);
                    return ResponseEntity.ok("Traseu șters.");
                })
                .orElse(ResponseEntity.notFound().build());
    }


    // DTO intern pentru request
    public static class TrackRequest {
        private String name;
        private List<TrackPointDTO> points;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<TrackPointDTO> getPoints() {
            return points;
        }

        public void setPoints(List<TrackPointDTO> points) {
            this.points = points;
        }

        public static class TrackPointDTO {
            private double latitude;
            private double longitude;
            private long timestamp;

            public double getLatitude() {
                return latitude;
            }

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }

            public double getLongitude() {
                return longitude;
            }

            public void setLongitude(double longitude) {
                this.longitude = longitude;
            }

            public long getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(long timestamp) {
                this.timestamp = timestamp;
            }
        }
    }
}
