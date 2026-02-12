package com.project.back_end.controllers;

import com.project.back_end.models.Doctor;
import com.project.back_end.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    /**
     * Requirement: Retrieve availability with path variables, token validation, and ResponseEntity.
     * URL Pattern: /api/doctors/{id}/availability/{role}/{token}
     */
    @GetMapping("/{id}/availability/{role}/{token}")
    public ResponseEntity<List<String>> getDoctorAvailability(
            @PathVariable("id") Long doctorId,
            @PathVariable("role") String userRole,
            @PathVariable("token") String token,
            @RequestParam("date") String date) {

        // 1. Token Validation (Required for compliance)
        if (token == null || token.isEmpty() || !isValidToken(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // 2. Logic to fetch availability
        List<String> availability = doctorRepository.findAvailabilityByDoctorIdAndDate(doctorId, date);

        // 3. Structured Response using ResponseEntity
        return new ResponseEntity<>(availability, HttpStatus.OK);
    }

    // Helper method for token validation logic
    private boolean isValidToken(String token) {
        // Implement your actual JWT or session validation logic here
        return "valid-test-token".equals(token) || token.length() > 10;
    }

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
}
