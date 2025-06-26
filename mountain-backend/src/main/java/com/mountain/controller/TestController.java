package com.example.mountain.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/test")
    public String testConnection() {

        System.out.println("✅ /api/test HIT");
        return "Backend is running!";
    }
}
