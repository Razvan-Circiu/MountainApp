package com.mountain.controller;

import com.mountain.dto.TrackDTO;
import com.mountain.dto.TrackPointDTO;
import com.mountain.mapper.TrackPointMapper;
import com.mountain.model.Track;
import com.mountain.model.TrackPoint;
import com.mountain.model.User;
import com.mountain.repository.TrackRepository;
import com.mountain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/tracks")
@CrossOrigin
public class TrackController {

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadTrack(@RequestBody TrackDTO trackDto, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());

        Track track = new Track();
        track.setName(trackDto.getName());
        track.setDescription(trackDto.getDescription());
        track.setUser(user);

        List<TrackPoint> trackPoints = new ArrayList<>();
        for (TrackPointDTO dto : trackDto.getTrackPoints()) {
            TrackPoint point = TrackPointMapper.toEntity(dto);
            point.setTrack(track);
            trackPoints.add(point);
        }

        track.setPoints(trackPoints);
        trackRepository.save(track);

        return ResponseEntity.ok("Traseu încărcat cu succes");
    }

    @GetMapping
    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Track> getTrackById(@PathVariable Long id) {
        return trackRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/my")
    public List<Track> getUserTracks(Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        return trackRepository.findByUser(user);
    }
}
