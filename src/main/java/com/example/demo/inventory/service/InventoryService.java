package com.example.demo.inventory.service;

public interface InventoryService {

    void stockIn(Long productId, Integer quantity, Long userId);

    void stockOut(Long productId, Integer quantity, Long userId);
}
