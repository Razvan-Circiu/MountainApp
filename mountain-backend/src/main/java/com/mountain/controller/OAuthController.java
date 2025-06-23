package com.mountain.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

@RestController
public class OAuthController {

    @GetMapping("/api/success")
    public void loginSuccess(HttpServletRequest request,
                             HttpServletResponse response,
                             Authentication authentication) throws IOException, ServletException {

        if (authentication == null || !(authentication.getPrincipal() instanceof OAuth2User)) {
            response.sendRedirect("/api/failure");
            return;
        }

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        // Trimite userul către front-end cu parametri în URL
        URI redirectUri = UriComponentsBuilder
                .fromUriString("http://localhost:3000/login-success") // sau pagina ta de frontend
                .queryParam("email", email)
                .queryParam("name", name)
                .build()
                .toUri();

        response.sendRedirect(redirectUri.toString());
    }

    @GetMapping("/api/failure")
    public String loginFailure() {
        return "Autentificare eșuată.";
    }
}
