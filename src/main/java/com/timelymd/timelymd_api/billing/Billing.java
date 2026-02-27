package com.timelymd.timelymd_api.billing;

import com.timelymd.timelymd_api.appointment.Appointment;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    @OneToMany(mappedBy = "billing", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<BillingItem> items = new ArrayList<>();

    @Column(precision = 10, scale = 2)
    private BigDecimal subtotal = BigDecimal.ZERO;

    @Column(precision = 10, scale = 2)
    private BigDecimal tax = BigDecimal.ZERO;

    @Column(precision = 10, scale = 2)
    private BigDecimal discount = BigDecimal.ZERO;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Column(precision = 10, scale = 2)
    private BigDecimal amountPaid = BigDecimal.ZERO;

    @Column(precision = 10, scale = 2)
    private BigDecimal balanceDue = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus; // PAID, PARTIAL, PENDING, OVERDUE

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod; // CASH, CARD, INSURANCE, BANK_TRANSFER

    private String insuranceClaimId; // If using insurance

    public void addItem(BillingItem item) {
        items.add(item);
        item.setBilling(this);
        recalculateTotals();
    }

    public void removeItem(BillingItem item) {
        items.remove(item);
        item.setBilling(null);
        recalculateTotals();
    }

    private void recalculateTotals() {
        // Calculate subtotal from items
        this.subtotal = items.stream()
                .map(BillingItem::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        // Calculate total: subtotal - discount + tax
        this.totalAmount = subtotal
                .subtract(discount)
                .add(tax)
                .setScale(2, RoundingMode.HALF_UP);

        // Calculate balance due
        this.balanceDue = totalAmount
                .subtract(amountPaid)
                .setScale(2, RoundingMode.HALF_UP);

        // Update payment status based on balance
        updatePaymentStatus();
    }

    private void updatePaymentStatus() {
        if (balanceDue.compareTo(BigDecimal.ZERO) == 0) {
            this.paymentStatus = PaymentStatus.PAID;
        } else if (amountPaid.compareTo(BigDecimal.ZERO) > 0
                && amountPaid.compareTo(totalAmount) < 0) {
            this.paymentStatus = PaymentStatus.PARTIAL;
        } else if (balanceDue.compareTo(BigDecimal.ZERO) > 0
                && billingDate.plusDays(30).isBefore(LocalDateTime.now())) {
            this.paymentStatus = PaymentStatus.OVERDUE;
        } else {
            this.paymentStatus = PaymentStatus.PENDING;
        }
    }

    public void makePayment(BigDecimal paymentAmount) {
        if (paymentAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive");
        }

        this.amountPaid = amountPaid.add(paymentAmount)
                .setScale(2, RoundingMode.HALF_UP);
        recalculateTotals();
    }

    public void applyDiscount(BigDecimal discountAmount) {
        this.discount = discountAmount.setScale(2, RoundingMode.HALF_UP);
        recalculateTotals();
    }

    public void applyTax(BigDecimal taxAmount) {
        this.tax = taxAmount.setScale(2, RoundingMode.HALF_UP);
        recalculateTotals();
    }
}