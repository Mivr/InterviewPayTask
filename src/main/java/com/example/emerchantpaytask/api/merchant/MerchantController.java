package com.example.emerchantpaytask.api.merchant;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.emerchantpaytask.security.CustomUserDetailsService;
import com.example.emerchantpaytask.security.repository.User;
import com.example.emerchantpaytask.service.merchant.MerchantService;

@RestController
@RequestMapping("/api/merchants")
@PreAuthorize("hasRole('ADMIN')")
public class MerchantController {

    private final CustomUserDetailsService customUserDetailsService;
    private final MerchantService merchantService;

    public MerchantController(MerchantService merchantService, CustomUserDetailsService customUserDetailsService) {
        this.merchantService = merchantService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping
    public ResponseEntity<List<Merchant>> getAllMerchants() {
        return ResponseEntity.ok(merchantService.getAllMerchants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Merchant> getMerchant(@PathVariable Long id) {
        return merchantService.getMerchant(id)
                              .map(ResponseEntity::ok)
                              .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Merchant> createMerchant(@RequestBody Merchant merchant) {
        // TODO, names are not unique, here we should be using the IDs, but for demonstration purposes this is fine
        customUserDetailsService.createUser(new User(null, merchant.name(), "password"));

        return ResponseEntity.ok(merchantService.createMerchant(merchant));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Merchant> updateMerchant(@PathVariable Long id, @RequestBody Merchant merchant) {
        return ResponseEntity.ok(merchantService.updateMerchant(id, merchant));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchant(@PathVariable Long id) {
        merchantService.deleteMerchant(id);
        return ResponseEntity.noContent().build();
    }
}
