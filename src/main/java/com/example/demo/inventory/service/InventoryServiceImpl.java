package com.example.demo.inventory.service;

import com.example.demo.inventory.entity.InventoryTransaction;
import com.example.demo.inventory.entity.TransactionType;
import com.example.demo.inventory.repository.InventoryTransactionRepository;
import com.example.demo.product.entity.Product;
import com.example.demo.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private final ProductRepository productRepository;
    private final InventoryTransactionRepository transactionRepository;

    public InventoryServiceImpl(ProductRepository productRepository,
                                InventoryTransactionRepository transactionRepository) {
        this.productRepository = productRepository;
        this.transactionRepository = transactionRepository;
    }

    // -----------------------
    // STOCK IN
    // -----------------------
    @Override
    public void stockIn(Long productId, Integer quantity, Long userId) {

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Product not found with id: " + productId));

        // Increase stock
        product.setQuantity(product.getQuantity() + quantity);
        productRepository.save(product);

        // Log transaction
        InventoryTransaction tx = new InventoryTransaction();
        tx.setProductId(productId);
        tx.setUserId(userId);
        tx.setQuantity(quantity);
        tx.setType(TransactionType.STOCK_IN);

        transactionRepository.save(tx);
    }

    // -----------------------
    // STOCK OUT
    // -----------------------
    @Override
    public void stockOut(Long productId, Integer quantity, Long userId) {

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Product not found with id: " + productId));

        if (product.getQuantity() < quantity) {
            throw new IllegalStateException("Insufficient stock available");
        }

        // Decrease stock
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);

        // Log transaction
        InventoryTransaction tx = new InventoryTransaction();
        tx.setProductId(productId);
        tx.setUserId(userId);
        tx.setQuantity(quantity);
        tx.setType(TransactionType.STOCK_OUT);

        transactionRepository.save(tx);
    }
}
