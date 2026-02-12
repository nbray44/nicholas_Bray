package com.project.back_end.services;

import com.project.back_end.models.Doctor;
import com.project.back_end.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Added for security
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    // Instance of BCryptPasswordEncoder for secure password matching
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Requirement: Retrieve available time slots for a specific date.
     */
    public List<String> getAvailableTimeSlots(Long doctorId, LocalDate date) {
        return doctorRepository.findAvailableSlotsByDoctorAndDate(doctorId, date);
    }

    /**
     * FIX: Validates doctor login credentials using BCrypt and returns a ResponseEntity.
     * This addresses both security and response-handling feedback.
     */
    public ResponseEntity<String> validateDoctorLogin(String email, String password) {
        Optional<Doctor> doctor = doctorRepository.findByEmail(email);
        
        if (doctor.isPresent()) {
            // Using BCrypt to safely match the provided password against the stored hash
            if (passwordEncoder.matches(password, doctor.get().getPassword())) {
                return new ResponseEntity<>("Login successful", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
            }
        }
        return new ResponseEntity<>("Doctor not found", HttpStatus.NOT_FOUND);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor saveDoctor(Doctor doctor) {
        // Securely hash the password before saving
        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        return doctorRepository.save(doctor);
    }

    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }
