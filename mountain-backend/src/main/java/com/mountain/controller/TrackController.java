package com.mountain.controller;

import com.mountain.dto.TrackDTO;
import com.mountain.mapper.TrackMapper;
import com.mountain.model.Track;
import com.mountain.model.User;
import com.mountain.repository.TrackRepository;
import com.mountain.repository.UserRepository;
import com.mountain.service.GpxService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tracks")
@CrossOrigin
public class TrackController {

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GpxService gpxService;

    @GetMapping
    public List<TrackDTO> getAllTracks() {
        return trackRepository.findAll()
                .stream()
                .map(TrackMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TrackDTO getTrackById(@PathVariable Long id) {
        Track track = trackRepository.findById(id).orElseThrow();
        return TrackMapper.toDto(track);
    }

    @GetMapping("/me")
    public List<TrackDTO> getUserTracks(Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        return trackRepository.findByUser(user)
                .stream()
                .map(TrackMapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public void uploadTrack(@RequestBody TrackDTO dto, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        Track track = TrackMapper.fromDto(dto);
        track.setUser(user);
        trackRepository.save(track);
    }

    @GetMapping("/{id}/gpx")
    public void downloadGpx(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Track track = trackRepository.findById(id).orElseThrow();
        gpxService.writeTrackAsGpx(track, response);
    }
}
