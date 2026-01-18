package com.example.demo.product.service;

import com.example.demo.product.dto.ProductCreateRequest;
import com.example.demo.product.dto.ProductResponse;
import com.example.demo.product.dto.ProductUpdateRequest;
import com.example.demo.product.entity.Product;
import com.example.demo.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // ------------------------
    // CREATE PRODUCT
    // ------------------------
    @Override
    public ProductResponse createProduct(ProductCreateRequest request) {

        if (productRepository.existsBySku(request.getSku())) {
            throw new IllegalArgumentException("SKU already exists: " + request.getSku());
        }

        Product product = new Product();
        product.setName(request.getName());
        product.setSku(request.getSku());
        product.setCategory(request.getCategory());
        product.setSupplier(request.getSupplier());
        product.setQuantity(request.getQuantity());
        product.setMinThreshold(request.getMinThreshold());
        product.setPrice(request.getPrice());

        Product savedProduct = productRepository.save(product);

        return mapToResponse(savedProduct);
    }

    // ------------------------
    // UPDATE PRODUCT
    // ------------------------
    @Override
    public ProductResponse updateProduct(Long productId, ProductUpdateRequest request) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));

        product.setName(request.getName());
        product.setCategory(request.getCategory());
        product.setSupplier(request.getSupplier());
        product.setMinThreshold(request.getMinThreshold());
        product.setPrice(request.getPrice());

        return mapToResponse(productRepository.save(product));
    }

    // ------------------------
    // DELETE PRODUCT
    // ------------------------
    @Override
    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException("Product not found with id: " + productId);
        }
        productRepository.deleteById(productId);
    }

    // ------------------------
    // GET BY ID
    // ------------------------
    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));

        return mapToResponse(product);
    }

    // ------------------------
    // GET ALL (PAGINATION)
    // ------------------------
    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    // ------------------------
    // SEARCH FILTERS
    // ------------------------
    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> searchByCategory(String category, Pageable pageable) {
        return productRepository.findByCategoryIgnoreCase(category, pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> searchBySupplier(String supplier, Pageable pageable) {
        return productRepository.findBySupplierIgnoreCase(supplier, pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> searchByName(String name, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(this::mapToResponse);
    }

    // ------------------------
    // ENTITY → DTO MAPPER
    // ------------------------
    private ProductResponse mapToResponse(Product product) {

        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setSku(product.getSku());
        response.setCategory(product.getCategory());
        response.setSupplier(product.getSupplier());
        response.setQuantity(product.getQuantity());
        response.setMinThreshold(product.getMinThreshold());
        response.setPrice(product.getPrice());
        response.setCreatedAt(product.getCreatedAt());

        return response;
    }
}
