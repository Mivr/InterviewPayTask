package com.example.emerchantpaytask.service.transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.example.emerchantpaytask.api.transaction.Transaction;

@Service
public class TransactionService {

    private final Map<UUID, Transaction> transactions = new ConcurrentHashMap<>();

    public TransactionService() {
        // Initialize with dummy data
        createTransaction(
            new Transaction(null, 100.50, Transaction.TransactionStatus.APPROVED, "john@example.com", "+1234567890",
                            "REF001"));
        createTransaction(
            new Transaction(null, 75.25, Transaction.TransactionStatus.APPROVED, "jane@example.com", "+1987654321",
                            "REF002"));
        createTransaction(
            new Transaction(null, 200.00, Transaction.TransactionStatus.REFUNDED, "bob@example.com", "+1122334455",
                            "REF003"));
        createTransaction(
            new Transaction(null, 50.75, Transaction.TransactionStatus.ERROR, "alice@example.com", "+1555666777",
                            "REF004"));
        createTransaction(
            new Transaction(null, 150.00, Transaction.TransactionStatus.REVERSED, "charlie@example.com", "+1999888777",
                            "REF005"));
    }

    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions.values());
    }

    public Optional<Transaction> getTransaction(UUID uuid) {
        return Optional.ofNullable(transactions.get(uuid));
    }

    public Transaction createTransaction(Transaction transaction) {
        UUID uuid = UUID.randomUUID();
        Transaction newTransaction = new Transaction(
            uuid,
            transaction.amount(),
            transaction.status(),
            transaction.customerEmail(),
            transaction.customerPhone(),
            transaction.referenceId()
        );
        transactions.put(uuid, newTransaction);
        return newTransaction;
    }
}
