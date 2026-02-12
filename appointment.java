package com.project.back_end.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Data // Use this if you have Lombok, otherwise generate standard Getters/Setters
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Requirement: Must be named 'appointmentTime' with correct validation
    @NotNull(message = "Appointment time is required")
    @Future(message = "Appointment must be in the future")
    private LocalDateTime appointmentTime;

    @Size(max = 255)
    private String description;

    // Requirement: Relationship to Doctor using @ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    @NotNull(message = "Doctor is required")
    private Doctor doctor;

    // Requirement: Relationship to Patient using @ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    @NotNull(message = "Patient is required")
    private Patient patient;

    // Constructors
    public Appointment() {}
}

