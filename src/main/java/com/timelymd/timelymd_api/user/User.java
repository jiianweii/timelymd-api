package com.timelymd.timelymd_api.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String profileUrl;

    @Column(unique = true, nullable = false)
    private String supabaseUserId;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Doctor doctorProfile;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Staff staffProfile;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Owner ownerProfile;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Patient patientProfile;

    // Helper methods to check role
    public boolean isDoctor() { return role == Role.DOCTOR; }
    public boolean isStaff() { return role == Role.STAFF; }
    public boolean isOwner() { return role == Role.OWNER; }
    public boolean isPatient() { return role == Role.PATIENT; }

}
