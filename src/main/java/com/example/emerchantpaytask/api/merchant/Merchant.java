package com.example.emerchantpaytask.api.merchant;

public record Merchant(
    Long id,
    String name,
    String description,
    String email,
    MerchantStatus status,
    Double totalTransactionSum
) {
    public enum MerchantStatus {
        ACTIVE, INACTIVE
    }
}