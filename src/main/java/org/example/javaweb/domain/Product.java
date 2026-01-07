package org.example.javaweb.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String name;

    @Size(max = 2000)
    @Column(length = 2000)
    private String description;

    @NotNull
    @DecimalMin(value = "0.00", inclusive = true)
    @Digits(integer = 10, fraction = 2)
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @NotNull
    private Category category;

    @Min(0)
    @Column(nullable = false)
    private int stock;
}
