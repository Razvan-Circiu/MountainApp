package com.mountain.controller;

import com.mountain.model.Trail;
import com.mountain.repository.TrailRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trails")
@CrossOrigin
public class TrailController {

    private final TrailRepository repo;

    public TrailController(TrailRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Trail> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public Trail create(@RequestBody Trail trail) {
        return repo.save(trail);
    }
}
