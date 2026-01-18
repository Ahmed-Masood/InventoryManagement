package com.example.demo.inventory.controller;

import com.example.demo.inventory.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.demo.inventory.dto.StockRequest;
import jakarta.validation.Valid;


import java.security.Principal;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "*")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @PostMapping("/stock-in")
    public ResponseEntity<String> stockIn(
            @Valid @RequestBody StockRequest request,
            Principal principal) {

        Long userId = 1L; // temporary

        inventoryService.stockIn(
                request.getProductId(),
                request.getQuantity(),
                userId
        );

        return ResponseEntity.ok("Stock added successfully");
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @PostMapping("/stock-out")
    public ResponseEntity<String> stockOut(
            @Valid @RequestBody StockRequest request,
            Principal principal) {

        Long userId = 1L; // temporary

        inventoryService.stockOut(
                request.getProductId(),
                request.getQuantity(),
                userId
        );

        return ResponseEntity.ok("Stock removed successfully");
    }
}

