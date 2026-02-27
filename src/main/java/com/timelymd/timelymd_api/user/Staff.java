package com.timelymd.timelymd_api.user;

import com.timelymd.timelymd_api.clinic.Clinic;
import jakarta.persistence.*;

import java.time.LocalDate;

// Staff.java
@Entity
@Table(name = "staff")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    // Staff-specific fields
    private String position;  // "Receptionist", "Nurse", "Administrator"
    private String employeeId;
    private LocalDate hireDate;
    private String department;
}