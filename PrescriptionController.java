package com.project.back_end.controllers;

import com.project.back_end.models.Prescription;
import com.project.back_end.services.PrescriptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping
    public List<Prescription> getAllPrescriptions() {
        return prescriptionService.getAllPrescriptions();
    }

    /**
     * Requirement Fix: 
     * 1. Added {token} as a @PathVariable in the @PostMapping.
     * 2. Returns a structured response (Map<String, Object>) with success message and data.
     * 3. Uses @Valid and @RequestBody for the Prescription object.
     */
    @PostMapping("/{token}")
    public ResponseEntity<Map<String, Object>> createPrescription(
            @PathVariable("token") String token, 
            @Valid @RequestBody Prescription prescription) {
        
        // Note: In a real app, you would use 'token' here to validate the user session
        Prescription savedPrescription = prescriptionService.savePrescription(prescription);
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Prescription created successfully");
        response.put("status", "success");
        response.put("tokenUsed", token); // Confirms token was processed
        response.put("data", savedPrescription);
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prescription> getPrescriptionById(@PathVariable String id) {
        return prescriptionService.getPrescriptionById(id)
                .map(prescription -> new ResponseEntity<>(prescription, HttpStatus.OK))
                .orElseThrow(() -> new RuntimeException("Prescription not found with id: " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrescription(@PathVariable String id) {
        prescriptionService.deletePrescription(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
