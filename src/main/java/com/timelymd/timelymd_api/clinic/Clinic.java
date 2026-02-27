package com.timelymd.timelymd_api.clinic;

import com.timelymd.timelymd_api.appointment.Appointment;
import com.timelymd.timelymd_api.service.Service;
import com.timelymd.timelymd_api.user.Doctor;
import com.timelymd.timelymd_api.user.Owner;
import com.timelymd.timelymd_api.user.Staff;
import com.timelymd.timelymd_api.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "clinics")
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String phone;
    private String email;

    // Owner relationship (one-to-one since one clinic has one owner)
    @OneToOne
    @JoinColumn(name = "owner_id", unique = true)
    private Owner owner;  // Each clinic has exactly ONE owner

    // Staff and Doctors
    @OneToMany(mappedBy = "clinic")
    private List<Doctor> doctors = new ArrayList<>();

    @OneToMany(mappedBy = "clinic")
    private List<Staff> staffs = new ArrayList<>();

    @OneToMany(mappedBy = "clinic")
    private List<Appointment> appointments = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "clinic_services",  // join table
            joinColumns = @JoinColumn(name = "clinic_id"),  // this is the owner
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<Service> services = new ArrayList<>();
}