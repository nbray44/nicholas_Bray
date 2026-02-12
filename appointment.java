package com.project.back_end.models;

import java.util.Date;

/**
 * Model class representing an Appointment for the Database Capstone Project.
 */
public class Appointment {
    private String appointmentId;
    private Date appointmentDate;
    private String description;

    // Default Constructor
    public Appointment() {}

    // Parameterized Constructor
    public Appointment(String appointmentId, Date appointmentDate, String description) {
        setAppointmentId(appointmentId);
        setAppointmentDate(appointmentDate);
        setDescription(description);
    }

    // Getter and Setter for appointmentId with basic validation
    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        if (appointmentId == null || appointmentId.length() > 10) {
            throw new IllegalArgumentException("Invalid Appointment ID");
        }
        this.appointmentId = appointmentId;
    }

    // Getter and Setter for appointmentDate
    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        // Ensure the date is not in the past
        if (appointmentDate == null || appointmentDate.before(new Date())) {
            throw new IllegalArgumentException("Invalid Appointment Date");
        }
        this.appointmentDate = appointmentDate;
    }

    // Getter and Setter for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.length() > 50) {
            throw new IllegalArgumentException("Invalid Description");
        }
        this.description = description;
    }

    @Override
    public String toString() {
        return "Appointment [ID=" + appointmentId + ", Date=" + appointmentDate + 
               ", Description=" + description + "]";
    }
}
