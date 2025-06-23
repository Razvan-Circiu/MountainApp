package com.mountain.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String home() {
        return "✅ Mountain API backend is live!";
    }

    @GetMapping("/api/ping")
    public String ping() {
        return "🏔️ Ping successful from Mountain API.";
    }
}
