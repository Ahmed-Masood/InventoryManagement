package com.example.demo.product.repository;

import com.example.demo.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Find product by SKU (used for uniqueness validation)
    Optional<Product> findBySku(String sku);

    // Pagination + filtering
    Page<Product> findByCategoryIgnoreCase(String category, Pageable pageable);

    Page<Product> findBySupplierIgnoreCase(String supplier, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // SKU existence check
    boolean existsBySku(String sku);
}
