package com.project.back_end.services;

import com.project.back_end.models.Appointment;
import com.project.back_end.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    /**
     * Retrieves all appointments from the database.
     */
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    /**
     * Saves a new appointment or updates an existing one.
     */
    public Appointment saveAppointment(Appointment appointment) {
        // Business logic check: Ensure the appointment date is not null
        if (appointment.getAppointmentDate() == null) {
            throw new IllegalArgumentException("Appointment date cannot be null");
        }
        return appointmentRepository.save(appointment);
    }

    /**
     * Finds a single appointment by its unique ID.
     */
    public Optional<Appointment> getAppointmentById(String id) {
        return appointmentRepository.findById(id);
    }

    /**
     * Deletes an appointment record by ID.
     */
    public void deleteAppointment(String id) {
        appointmentRepository.deleteById(id);
    }
}
