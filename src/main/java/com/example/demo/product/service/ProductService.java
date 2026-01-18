package com.example.demo.product.service;

import com.example.demo.product.dto.ProductCreateRequest;
import com.example.demo.product.dto.ProductResponse;
import com.example.demo.product.dto.ProductUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    ProductResponse createProduct(ProductCreateRequest request);

    ProductResponse updateProduct(Long productId, ProductUpdateRequest request);

    void deleteProduct(Long productId);

    ProductResponse getProductById(Long productId);

    Page<ProductResponse> getAllProducts(Pageable pageable);

    Page<ProductResponse> searchByCategory(String category, Pageable pageable);

    Page<ProductResponse> searchBySupplier(String supplier, Pageable pageable);

    Page<ProductResponse> searchByName(String name, Pageable pageable);
}
