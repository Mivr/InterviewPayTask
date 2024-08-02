package com.example.emerchantpaytask.domain;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    @Test
    void emptyListLeadsToErrorState() {
        Payment payment = new Payment(List.of());

        assertEquals(PaymentStatus.ERROR, payment.getStatus());
    }

    @Test
    void authorizationAndChargeLeadsToApprovedState() {
        Transaction authorizeTransaction = new Transaction(UUID.randomUUID(), 1.0, TransactionType.AUTHORIZATION, Instant.now());
        Transaction chargeTransaction = new Transaction(UUID.randomUUID(), 1.0, TransactionType.CHARGE, Instant.now());
        Payment payment = new Payment(List.of(authorizeTransaction, chargeTransaction));

        assertEquals(PaymentStatus.APPROVED, payment.getStatus());
    }

    @Test
    void chargeAndThenAuthorizationLeadsToErrorState() {
        Transaction chargeTransaction = new Transaction(UUID.randomUUID(), 1.0, TransactionType.CHARGE, Instant.now());
        Transaction authorizeTransaction = new Transaction(UUID.randomUUID(), 1.0, TransactionType.AUTHORIZATION, Instant.now().plusSeconds(1));
        Payment payment = new Payment(List.of(chargeTransaction, authorizeTransaction));

        assertEquals(PaymentStatus.ERROR, payment.getStatus());
    }

    @Test
    void anyDuplicatedTransactionTypeLeadsToErrorState() {
        Transaction authorizeTransaction = new Transaction(UUID.randomUUID(), 1.0, TransactionType.AUTHORIZATION, Instant.now());
        Transaction authorizeTransactionTwo = new Transaction(UUID.randomUUID(), 1.0, TransactionType.AUTHORIZATION, Instant.now());

        Payment payment = new Payment(List.of(authorizeTransaction, authorizeTransactionTwo));

        assertEquals(PaymentStatus.ERROR, payment.getStatus());

        Transaction chargeTransaction = new Transaction(UUID.randomUUID(), 1.0, TransactionType.CHARGE, Instant.now());
        Transaction chargeTransactionTwo = new Transaction(UUID.randomUUID(), 1.0, TransactionType.CHARGE, Instant.now());

        Payment paymentCharge = new Payment(List.of(chargeTransaction, chargeTransactionTwo));

        assertEquals(PaymentStatus.ERROR, paymentCharge.getStatus());

        Transaction refundTransaction = new Transaction(UUID.randomUUID(), 1.0, TransactionType.REFUND, Instant.now());
        Transaction refundTransactionTwo = new Transaction(UUID.randomUUID(), 1.0, TransactionType.REFUND, Instant.now());

        Payment paymentRefund = new Payment(List.of(refundTransaction, refundTransactionTwo));

        assertEquals(PaymentStatus.ERROR, paymentRefund.getStatus());

        Transaction reversalTransaction = new Transaction(UUID.randomUUID(), 1.0, TransactionType.REVERSAL, Instant.now());
        Transaction reversalTransactionTwo = new Transaction(UUID.randomUUID(), 1.0, TransactionType.REVERSAL, Instant.now());

        Payment paymentReversal = new Payment(List.of(reversalTransaction, reversalTransactionTwo));

        assertEquals(PaymentStatus.ERROR, paymentReversal.getStatus());
    }

    @Test
    void authorizeAndReverseLeadsToReversedState() {
        Transaction authorizeTransaction = new Transaction(UUID.randomUUID(), 1.0, TransactionType.AUTHORIZATION, Instant.now());
        Transaction reverseTransaction = new Transaction(UUID.randomUUID(), 1.0, TransactionType.REVERSAL, Instant.now().plusSeconds(1));
        Payment payment = new Payment(List.of(authorizeTransaction, reverseTransaction));

        assertEquals(PaymentStatus.REVERSED, payment.getStatus());
    }

    @Test
    void authorizationChargeAndRefundLeadsToRefundedState() {
        Transaction authorizeTransaction = new Transaction(UUID.randomUUID(), 1.0, TransactionType.AUTHORIZATION, Instant.now());
        Transaction chargeTransaction = new Transaction(UUID.randomUUID(), 1.0, TransactionType.CHARGE, Instant.now().plusSeconds(1));
        Transaction refundTransaction = new Transaction(UUID.randomUUID(), 1.0, TransactionType.REFUND, Instant.now().plusSeconds(2));
        Payment payment = new Payment(List.of(authorizeTransaction, chargeTransaction, refundTransaction));

        assertEquals(PaymentStatus.REFUNDED, payment.getStatus());
    }

    @Test
    void fourTransactionsLeadToErrorState() {
        Transaction authorizeTransaction = new Transaction(UUID.randomUUID(), 1.0, TransactionType.AUTHORIZATION, Instant.now());
        Transaction chargeTransaction = new Transaction(UUID.randomUUID(), 1.0, TransactionType.CHARGE, Instant.now().plusSeconds(1));
        Transaction refundTransaction = new Transaction(UUID.randomUUID(), 1.0, TransactionType.REFUND, Instant.now().plusSeconds(2));
        Transaction reversalTransaction = new Transaction(UUID.randomUUID(), 1.0, TransactionType.REVERSAL, Instant.now().plusSeconds(3));
        Payment payment = new Payment(List.of(authorizeTransaction, chargeTransaction, refundTransaction, reversalTransaction));

        assertEquals(PaymentStatus.ERROR, payment.getStatus());
    }
}