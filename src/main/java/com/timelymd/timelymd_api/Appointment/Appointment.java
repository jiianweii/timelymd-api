package com.timelymd.timelymd_api.Appointment;

import com.timelymd.timelymd_api.Billing.Billing;
import com.timelymd.timelymd_api.Clinic.Clinic;
import com.timelymd.timelymd_api.Service.Service;
import com.timelymd.timelymd_api.User.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    @NotEmpty
    private LocalDateTime appointmentDateTime;
    @NotEmpty
    private String reason;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patientId")
    private User patient;  // The patient

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctorId")
    private User doctor;   // The doctor

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinicId")
    private Clinic clinic;

    @NotEmpty
    @Enumerated(EnumType.STRING)
    private Status status;

    @NonNull
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "appointment_services",
            joinColumns = @JoinColumn(name = "appointmentId"),
            inverseJoinColumns = @JoinColumn(name = "serviceId")
    )
    private Set<Service> services = new HashSet<>();  // Multiple services per appointment

    @NonNull
    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Billing billing;  // One bill per appointment

    @NotEmpty
    private String notes;

    public void setBilling(Billing billing) {
        this.billing = billing;
        if (billing != null) {
            billing.setAppointment(this);
        }
    }
}
