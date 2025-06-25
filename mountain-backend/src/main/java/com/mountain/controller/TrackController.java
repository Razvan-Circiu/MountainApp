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
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus; // <-- adaugă aceasta
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/{trackId}")
    public ResponseEntity<TrackDTO> getTrackById(@PathVariable Long trackId) {
        Optional<Track> optionalTrack = trackRepository.findById(trackId);
        return optionalTrack.map(track -> ResponseEntity.ok(TrackMapper.toDto(track)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createTrack(@RequestBody TrackDTO dto, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());

        Track track = TrackMapper.fromDto(dto);
        track.setUser(user);

        Track savedTrack = trackRepository.save(track);
        return ResponseEntity.ok(TrackMapper.toDto(savedTrack));
    }

    @GetMapping("/{trackId}/download")
    public void downloadTrack(@PathVariable Long trackId, HttpServletResponse response) {
        Optional<Track> trackOpt = trackRepository.findById(trackId);
        if (trackOpt.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        gpxService.writeTrackAsGpx(trackOpt.get(), response);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadGpx(@RequestParam("file") MultipartFile file, Principal principal) {
        try {
            // Obține userul autentificat
            User user = userRepository.findByEmail(principal.getName());

            // Parsează fișierul GPX și creează obiectul Track
            Track track = gpxService.parseGpx(file.getInputStream(), user);

            // Salvează track-ul în baza de date
            trackRepository.save(track);

            return ResponseEntity.ok("Traseu încărcat cu succes.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Eroare la procesarea fișierului GPX: " + e.getMessage());
        }
    }

}
