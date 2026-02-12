package com.project.back_end.controllers;

import com.project.back_end.models.Prescription;
import com.project.back_end.services.PrescriptionService;
import jakarta.validation.Valid; // Added for validation
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // Added for response status
import org.springframework.http.ResponseEntity; // Added for structured response
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    // GET endpoint to retrieve all prescriptions
    @GetMapping
    public List<Prescription> getAllPrescriptions() {
        return prescriptionService.getAllPrescriptions();
    }

    /**
     * Requirement Fix: 
     * 1. Added @Valid to validate the incoming Prescription object.
     * 2. Changed return type to ResponseEntity for structured status codes.
     */
    @PostMapping
    public ResponseEntity<Prescription> createPrescription(@Valid @RequestBody Prescription prescription) {
        Prescription savedPrescription = prescriptionService.savePrescription(prescription);
        return new ResponseEntity<>(savedPrescription, HttpStatus.CREATED);
    }

    // GET endpoint to retrieve a single prescription by ID
    @GetMapping("/{id}")
    public ResponseEntity<Prescription> getPrescriptionById(@PathVariable String id) {
        return prescriptionService.getPrescriptionById(id)
                .map(prescription -> new ResponseEntity<>(prescription, HttpStatus.OK))
                .orElseThrow(() -> new RuntimeException("Prescription not found with id: " + id));
    }

    // DELETE endpoint to remove a prescription
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrescription(@PathVariable String id) {
        prescriptionService.deletePrescription(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
