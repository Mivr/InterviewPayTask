package com.example.emerchantpaytask.domain;

import java.time.Instant;
import java.util.UUID;

public record Transaction(
    UUID id,
    Double amount,
    TransactionType transactionType,
    Instant timestamp
) {
}
