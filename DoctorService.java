package com.project.back_end.services;

import com.project.back_end.models.Doctor;
import com.project.back_end.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing Doctor business logic.
 */
@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    // Retrieve all doctors
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    // Save or update a doctor record
    public Doctor saveDoctor(Doctor doctor) {
        if (doctor.getName() == null || doctor.getName().isEmpty()) {
            throw new IllegalArgumentException("Doctor name cannot be empty");
        }
        return doctorRepository.save(doctor);
    }

    // Find a specific doctor by ID
    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

    // Delete a doctor record
    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }
}
