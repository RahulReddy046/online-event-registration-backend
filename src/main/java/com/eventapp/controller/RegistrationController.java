package com.eventapp.controller;

import com.eventapp.dto.RegistrationDTO;
import com.eventapp.service.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/events/{eventId}")
    public ResponseEntity<RegistrationDTO> registerForEvent(
            @PathVariable Long eventId,
            @RequestBody(required = false) Map<String, String> body,
            Authentication authentication) {
        String email = authentication.getName();
        String utrNumber = (body != null) ? body.get("utrNumber") : null;
        return ResponseEntity.ok(registrationService.registerForEvent(eventId, email, utrNumber));
    }

    @DeleteMapping("/{registrationId}")
    public ResponseEntity<Void> cancelRegistration(
            @PathVariable Long registrationId,
            Authentication authentication) {
        registrationService.cancelRegistration(registrationId, authentication.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/my")
    public ResponseEntity<List<RegistrationDTO>> getMyRegistrations(
            Authentication authentication) {
        return ResponseEntity.ok(
                registrationService.getUserRegistrations(authentication.getName()));
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<List<RegistrationDTO>> getEventRegistrations(
            @PathVariable Long eventId) {
        return ResponseEntity.ok(registrationService.getEventRegistrations(eventId));
    }
}