package com.timelymd.timelymd_api.user;

import com.timelymd.timelymd_api.appointment.Appointment;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Patient.java
@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    // Patient-specific fields
    private LocalDate dateOfBirth;
    private String bloodType;
    private String allergies;
    private String emergencyContact;
    private String emergencyContactPhone;

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments = new ArrayList<>();
}