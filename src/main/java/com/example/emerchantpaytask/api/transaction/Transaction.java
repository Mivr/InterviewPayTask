package com.example.emerchantpaytask.api.transaction;

import java.util.UUID;

public record Transaction(
    UUID uuid,
    Double amount,
    TransactionStatus status,
    String customerEmail,
    String customerPhone,
    String referenceId
) {
    public enum TransactionStatus {
        APPROVED, REVERSED, REFUNDED, ERROR
    }
}
