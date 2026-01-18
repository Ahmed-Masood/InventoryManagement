package com.example.demo.inventory.repository;

import com.example.demo.inventory.entity.InventoryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryTransactionRepository
        extends JpaRepository<InventoryTransaction, Long> {

    // Fetch transactions for a product (audit history)
    List<InventoryTransaction> findByProductIdOrderByTimestampDesc(Long productId);

    // Fetch transactions for a user (audit trail)
    List<InventoryTransaction> findByUserIdOrderByTimestampDesc(Long userId);
}
