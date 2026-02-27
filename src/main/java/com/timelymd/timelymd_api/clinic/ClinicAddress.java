package com.timelymd.timelymd_api.clinic;

import jakarta.persistence.Embeddable;

@Embeddable
public class ClinicAddress {
    private String address;
    private Integer zipcode;
}
