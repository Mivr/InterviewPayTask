package com.example.emerchantpaytask.domain;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Payment {
    private List<Transaction> transactions;
    private Double amount;
    private PaymentStatus status;

    public Payment(List<Transaction> transactions) {
        this.transactions = transactions;
        this.amount = 0.0;

        this.status = computeStatus(transactions);
    }

    public Double amount() {
        return amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    private PaymentStatus computeStatus(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            return PaymentStatus.ERROR;
        }

        Map<TransactionType, Integer> transactionTypeCount = new HashMap<>();
        for (Transaction transaction : transactions) {
            transactionTypeCount.put(transaction.transactionType(),
                                     transactionTypeCount.getOrDefault(transaction.transactionType(), 0) + 1);
        }

        for (TransactionType transactionType : transactionTypeCount.keySet()) {
            if (transactionTypeCount.get(transactionType) > 1) {
                return PaymentStatus.ERROR;
            }
        }

        List<Transaction> orderedTransactions = List.copyOf(transactions).stream()
                                                    .sorted(Comparator.comparing(Transaction::timestamp)).
                                                    toList();

        if (orderedTransactions.size() > 3) {
            return PaymentStatus.ERROR;
        }

        if (orderedTransactions.get(0).transactionType() == TransactionType.AUTHORIZATION &&
            orderedTransactions.get(1).transactionType() == TransactionType.CHARGE && orderedTransactions.size() == 2) {
            return PaymentStatus.APPROVED;
        }

        if (orderedTransactions.get(0).transactionType() == TransactionType.AUTHORIZATION &&
            orderedTransactions.get(1).transactionType() == TransactionType.REVERSAL
            && orderedTransactions.size() == 2) {
            return PaymentStatus.REVERSED;
        }

        if (orderedTransactions.get(0).transactionType() == TransactionType.AUTHORIZATION &&
            orderedTransactions.get(1).transactionType() == TransactionType.CHARGE &&
            orderedTransactions.get(2).transactionType() == TransactionType.REFUND) {
            return PaymentStatus.REFUNDED;
        }

        return PaymentStatus.ERROR;
    }
}
