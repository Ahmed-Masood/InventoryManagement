package com.example.demo.product.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class ProductCreateRequest {

    @NotBlank(message = "Product name is required")
    @Size(max = 150)
    private String name;

    @NotBlank(message = "SKU is required")
    @Size(max = 50)
    private String sku;

    @Size(max = 100)
    private String category;

    @Size(max = 100)
    private String supplier;

    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    @Min(value = 0, message = "Minimum threshold cannot be negative")
    private Integer minThreshold;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than zero")
    private BigDecimal price;

    // Getters and Setters (Generate using Eclipse)
    public String getName() {
        return name;
    }

    public String getSku() {
        return sku;
    }

    public String getCategory() {
        return category;
    }

    public String getSupplier() {
        return supplier;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getMinThreshold() {
        return minThreshold;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setMinThreshold(Integer minThreshold) {
        this.minThreshold = minThreshold;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
