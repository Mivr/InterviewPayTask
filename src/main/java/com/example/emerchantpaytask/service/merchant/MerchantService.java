package com.example.emerchantpaytask.service.merchant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.example.emerchantpaytask.api.merchant.Merchant;

@Service
public class MerchantService {

    private final Map<Long, Merchant> merchants = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    public MerchantService() {
        // Initialize with dummy data
        createMerchant(new Merchant(null, "Acme Corp", "General goods retailer", "acme@example.com",
                                    Merchant.MerchantStatus.ACTIVE,
                                    10000.0));
        createMerchant(
            new Merchant(null, "TechGadgets", "Electronics store", "tech@gadgets.com", Merchant.MerchantStatus.ACTIVE,
                         25000.0));
        createMerchant(
            new Merchant(null, "FoodieHub", "Restaurant chain", "info@foodiehub.com", Merchant.MerchantStatus.INACTIVE,
                         5000.0));
        createMerchant(new Merchant(null, "FashionForward", "Clothing retailer", "fashion@forward.com",
                                    Merchant.MerchantStatus.ACTIVE, 15000.0));
        createMerchant(
            new Merchant(null, "GreenThumb", "Garden supplies", "green@thumb.com", Merchant.MerchantStatus.ACTIVE,
                         7500.0));
    }

    public List<Merchant> getAllMerchants() {
        return new ArrayList<>(merchants.values());
    }

    public Optional<Merchant> getMerchant(Long id) {
        return Optional.ofNullable(merchants.get(id));
    }

    public Merchant createMerchant(Merchant merchant) {
        Long id = idGenerator.incrementAndGet();
        Merchant newMerchant = new Merchant(
            id,
            merchant.name(),
            merchant.description(),
            merchant.email(),
            merchant.status(),
            merchant.totalTransactionSum()
        );
        merchants.put(id, newMerchant);
        return newMerchant;
    }

    public Merchant updateMerchant(Long id, Merchant merchant) {
        if (!merchants.containsKey(id)) {
            throw new RuntimeException("Merchant not found");
        }
        Merchant updatedMerchant = new Merchant(
            id,
            merchant.name(),
            merchant.description(),
            merchant.email(),
            merchant.status(),
            merchant.totalTransactionSum()
        );
        merchants.put(id, updatedMerchant);
        return updatedMerchant;
    }

    public void deleteMerchant(Long id) {
        if (!merchants.containsKey(id)) {
            throw new RuntimeException("Merchant not found");
        }
        merchants.remove(id);
    }
}