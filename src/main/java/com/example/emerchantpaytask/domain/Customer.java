package com.example.emerchantpaytask.domain;

import java.util.List;
import java.util.UUID;

public record Customer(UUID id, String email, List<String> phones, List<Payment> payments) {
}
