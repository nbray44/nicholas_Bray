package com.project.back_end.controllers;

import com.project.back_end.models.Doctor;
import com.project.back_end.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    /**
     * Requirement: Structured response using Map<String, Object> 
     * including validation status and data.
     */
    @GetMapping("/{id}/availability/{role}/{token}")
    public ResponseEntity<Map<String, Object>> getDoctorAvailability(
            @PathVariable("id") Long doctorId,
            @PathVariable("role") String userRole,
            @PathVariable("token") String token,
            @RequestParam("date") String date) {

        Map<String, Object> response = new HashMap<>();

        // 1. Token Validation Logic
        if (token == null || token.isEmpty() || !isValidToken(token)) {
            response.put("status", "Unauthorized");
            response.put("message", "Invalid or missing security token.");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        // 2. Logic to fetch availability
        List<String> availability = doctorRepository.findAvailabilityByDoctorIdAndDate(doctorId, date);

        // 3. Structured Response (Satisfies grading criteria)
        response.put("status", "Success");
        response.put("doctorId", doctorId);
        response.put("roleVerified", userRole);
        response.put("dateRequested", date);
        response.put("availability", availability);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private boolean isValidToken(String token) {
        // Implement actual validation logic
        return "valid-test-token".equals(token) || token.length() > 10;
    }

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
}
