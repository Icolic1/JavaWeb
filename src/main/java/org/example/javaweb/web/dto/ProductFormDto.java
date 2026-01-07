package org.example.javaweb.web.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class ProductFormDto {

    private Long id;

    @NotBlank
    @Size(max = 150)
    private String name;

    @Size(max = 2000)
    private String description;

    @NotNull
    @DecimalMin(value = "0.00", inclusive = true)
    @Digits(integer = 10, fraction = 2)
    private BigDecimal price;

    @NotNull
    private Long categoryId;

    @Min(0)
    private int stock;

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}
