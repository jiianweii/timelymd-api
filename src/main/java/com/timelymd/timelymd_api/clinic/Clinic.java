package com.timelymd.timelymd_api.clinic;

import com.timelymd.timelymd_api.service.Service;
import com.timelymd.timelymd_api.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

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

    @NotEmpty
    private String name;
    @NotEmpty
    private String address;
    @NotEmpty
    private String imageUrl;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "userId")
    private User owner;

    @NonNull
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "clinicService",
            joinColumns = @JoinColumn(name = "clinicId"),
            inverseJoinColumns = @JoinColumn(name = "serviceId")
    )
    private Set<Service> services;
}
