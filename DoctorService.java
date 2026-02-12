package com.project.back_end.services;

import com.project.back_end.models.Doctor;
import com.project.back_end.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private TokenService tokenService; // Assume TokenService is available for JWT generation

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Retrieves a list of available appointment time slots for a specific doctor on a given date.
     * This method queries the repository to filter out slots that are already booked.
     * 
     * @param doctorId The unique ID of the doctor
     * @param date The specific date for which availability is requested
     * @return List of available time strings (e.g., ["09:00 AM", "10:30 AM"])
     */
    public List<String> getAvailableTimeSlots(Long doctorId, LocalDate date) {
        return doctorRepository.findAvailableSlotsByDoctorAndDate(doctorId, date);
    }

    /**
     * Validates doctor credentials and returns a structured response.
     * If successful, it includes a JWT token for session management as requested in the feedback.
     *
     * @param email Doctor's login email
     * @param password Raw password provided by the user
     * @return ResponseEntity containing a status message and authentication token
     */
    public ResponseEntity<Map<String, Object>> validateDoctorLogin(String email, String password) {
        Map<String, Object> response = new HashMap<>();
        Optional<Doctor> doctorOpt = doctorRepository.findByEmail(email);
        
        if (doctorOpt.isPresent()) {
            Doctor doctor = doctorOpt.get();
            if (passwordEncoder.matches(password, doctor.getPassword())) {
                // Requirement Fix: Returning a structured response with a token
                String token = tokenService.generateToken(email);
                
                response.put("status", "success");
                response.put("message", "Login successful");
                response.put("token", token);
                response.put("doctorId", doctor.getId());
                
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("status", "error");
                response.put("message", "Invalid password provided.");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
        }
        
        response.put("status", "error");
        response.put("message", "Doctor account not found with the provided email.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
}
