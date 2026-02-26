package com.timelymd.timelymd_api.Service;

import com.timelymd.timelymd_api.Clinic.Clinic;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

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
    private long id;


    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotEmpty
    private Double price;

    @NonNull
    @ManyToMany(mappedBy = "services", fetch = FetchType.LAZY)
    private Set<Clinic> clinic;


}
