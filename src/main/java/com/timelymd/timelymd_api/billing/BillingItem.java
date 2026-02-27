package com.timelymd.timelymd_api.billing;

import com.timelymd.timelymd_api.service.Service;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

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
    @JoinColumn(name = "service_id")
    private Service service;

    private String description;

    @Column(nullable = false)
    private Integer quantity = 1;

    @Column(precision = 10, scale = 2)
    private BigDecimal unitPrice = BigDecimal.ZERO;

    @Column(precision = 10, scale = 2)
    private BigDecimal amount = BigDecimal.ZERO;  // quantity * unitPrice

    @PrePersist
    @PreUpdate
    private void calculateAmount() {
        if (quantity != null && unitPrice != null) {
            this.amount = BigDecimal.valueOf(quantity)
                    .multiply(unitPrice)
                    .setScale(2, RoundingMode.HALF_UP);
        }
    }

    // Convenience constructor
    public BillingItem(Service service, Integer quantity) {
        this.service = service;
        this.description = service.getName();
        this.quantity = quantity;
        this.unitPrice = service.getPrice(); // Assuming Service now uses BigDecimal
        calculateAmount();
    }
}