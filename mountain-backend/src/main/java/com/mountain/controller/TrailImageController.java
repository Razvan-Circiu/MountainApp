package com.mountain.controller;

import com.mountain.model.Trail;
import com.mountain.repository.TrailRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/trails")
@CrossOrigin
public class TrailImageController {

    private final TrailRepository trailRepo;

    public TrailImageController(TrailRepository trailRepo) {
        this.trailRepo = trailRepo;
    }

    @PostMapping("/{trailId}/image")
    public ResponseEntity<?> uploadImage(
            @PathVariable Long trailId,
            @RequestParam("image") MultipartFile file) {

        Optional<Trail> optionalTrail = trailRepo.findById(trailId);
        if (optionalTrail.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Creăm folderul dacă nu există
        File uploadDir = new File("uploads");
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Nume fișier unic
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File dest = new File(uploadDir, filename);

        try {
            file.transferTo(dest);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Eroare la salvarea imaginii.");
        }

        // Salvăm calea în baza de date
        Trail trail = optionalTrail.get();
        trail.setImagePath(filename);
        trailRepo.save(trail);

        return ResponseEntity.ok("Imagine încărcată.");
    }
}
