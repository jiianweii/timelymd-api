package com.timelymd.timelymd_api.BillingItem;

import com.timelymd.timelymd_api.Billing.Billing;
import com.timelymd.timelymd_api.Service.Service;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Entity
@Table(name = "billing_items")
public class BillingItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billing_id")
    private Billing billing;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serviceId")
    private Service service;

    private String description;

    private Integer quantity;

    private Double unitPrice;

    private Double amount;  // quantity * unitPrice

    @PrePersist
    @PreUpdate
    private void calculateAmount() {
        this.amount = quantity * unitPrice;
    }
}
