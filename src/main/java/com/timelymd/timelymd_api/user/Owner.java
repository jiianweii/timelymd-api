package com.timelymd.timelymd_api.user;

import com.timelymd.timelymd_api.clinic.Clinic;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

// Owner.java
@Entity
@Table(name = "owners")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    // Owner-specific fields
    private String businessLicense;
    private String taxId;
    private String companyName;

    @OneToMany(mappedBy = "owner")
    private List<Clinic> clinics = new ArrayList<>();  // One owner, many clinics
}