package com.example.emerchantpaytask.domain;

import java.util.List;
import java.util.UUID;

public record Merchant(
    UUID id,
    String name,
    String description,
    String email,
    Boolean active,
    List<Payment> payments) {

    public Double getBalance() {
        return payments.stream()
                       .filter(payment -> payment.getStatus() == PaymentStatus.APPROVED)
                       .map(Payment::amount)
                       .reduce(0.0, Double::sum);
    }
}
