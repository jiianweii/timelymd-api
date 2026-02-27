package com.timelymd.timelymd_api.service;

import com.timelymd.timelymd_api.clinic.Clinic;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "services")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotEmpty
    private BigDecimal price;

    @ManyToMany(mappedBy = "services",fetch = FetchType.LAZY)
    private Set<Clinic> clinics = new HashSet<>();
}
