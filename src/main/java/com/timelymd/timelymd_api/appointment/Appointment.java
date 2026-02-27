package com.timelymd.timelymd_api.appointment;

import com.timelymd.timelymd_api.clinic.Clinic;
import com.timelymd.timelymd_api.user.Doctor;
import com.timelymd.timelymd_api.user.Patient;
import com.timelymd.timelymd_api.user.Staff;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "clinic_id", nullable = false)
    private Clinic clinic;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "created_by_staff_id")
    private Staff createdBy;  // Staff who created it

    // Appointment details
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private Status status;  // SCHEDULED, COMPLETED, CANCELLED, NO_SHOW
    private String reason;  // Reason for visit
    private String notes;

    private String type; // E-Visit or Physical

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
