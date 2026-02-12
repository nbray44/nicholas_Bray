package com.project.back_end.services;

import com.project.back_end.models.Appointment;
import com.project.back_end.models.Doctor;
import com.project.back_end.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    /**
     * Requirement: A method specifically designed to book an appointment.
     * This wraps the save logic with a clear intent for the booking process.
     */
    public Appointment bookAppointment(Appointment appointment) {
        // Business logic for booking (e.g., check for double booking)
        appointment.setStatus("BOOKED");
        return appointmentRepository.save(appointment);
    }

    /**
     * Requirement: Retrieve appointments based on a specific doctor and date.
     */
    public List<Appointment> getAppointmentsByDoctorAndDate(Long doctorId, LocalDateTime date) {
        // This requires a custom method in your AppointmentRepository
        return appointmentRepository.findByDoctorIdAndAppointmentTime(doctorId, date);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public Optional<Appointment> getAppointmentById(String id) {
        return appointmentRepository.findById(id);
    }

    public void deleteAppointment(String id) {
        appointmentRepository.deleteById(id);
    }
}

