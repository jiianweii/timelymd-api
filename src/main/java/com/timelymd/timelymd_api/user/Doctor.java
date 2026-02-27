package com.timelymd.timelymd_api.user;

import com.timelymd.timelymd_api.appointment.Appointment;
import com.timelymd.timelymd_api.clinic.Clinic;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    // Doctor-specific fields
    @NotBlank
    private String specialization;
    @NotBlank
    private String licenseNumber;
    @NotBlank
    private String qualifications;
    @NotBlank
    private Integer yearsOfExperience;

    // A doctor can view their appointments
    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments = new ArrayList<>();
}