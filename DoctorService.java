package com.project.back_end.services;

import com.project.back_end.models.Doctor;
import com.project.back_end.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    /**
     * Requirement 1: Retrieve available time slots for a specific date.
     */
    public List<String> getAvailableTimeSlots(Long doctorId, LocalDate date) {
        // Implementation logic to filter doctor's base availability 
        // against already booked appointments for that date.
        return doctorRepository.findAvailableSlotsByDoctorAndDate(doctorId, date);
    }

    /**
     * Requirement 2: Validate doctor login credentials.
     */
    public boolean validateDoctorLogin(String email, String password) {
        Optional<Doctor> doctor = doctorRepository.findByEmail(email);
        if (doctor.isPresent()) {
            // In a real app, use BCrypt to match the encoded password
            return doctor.get().getPassword().equals(password);
        }
        return false;
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor saveDoctor(Doctor doctor) {
        if (doctor.getName() == null || doctor.getName().isEmpty()) {
            throw new IllegalArgumentException("Doctor name cannot be empty");
        }
        return doctorRepository.save(doctor);
    }

    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }
}
