package com.timelymd.timelymd_api.Billing;

import com.timelymd.timelymd_api.Appointment.Appointment;
import com.timelymd.timelymd_api.BillingItem.BillingItem;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Entity
@Table(name = "billings")
public class Billing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", unique = true)
    private Appointment appointment;

    private String invoiceNumber;

    private LocalDateTime billingDate;

    @OneToMany(mappedBy = "billing", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BillingItem> items = new ArrayList<>();

    private Double subtotal;

    private Double tax;

    private Double discount;

    private Double totalAmount;

    private Double amountPaid;

    private Double balanceDue;

    private String paymentStatus; // PAID, PARTIAL, PENDING, OVERDUE

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod; // CASH, CARD, INSURANCE, BANK_TRANSFER

    private String insuranceClaimId; // If using insurance

    public void addItem(BillingItem item) {
        items.add(item);
        item.setBilling(this);
        recalculateTotals();
    }

    private void recalculateTotals() {
        this.subtotal = items.stream()
                .mapToDouble(BillingItem::getAmount)
                .sum();
        this.totalAmount = subtotal - discount + tax;
        this.balanceDue = totalAmount - amountPaid;
    }
}
