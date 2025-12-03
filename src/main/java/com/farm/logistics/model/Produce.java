package com.farm.logistics.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "produce")
@Data
@NoArgsConstructor
public class Produce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @jakarta.validation.constraints.NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    @jakarta.validation.constraints.NotBlank(message = "Type is required")
    @Column(nullable = false)
    private String type; // e.g., Vegetable, Fruit, Grain

    @jakarta.validation.constraints.NotNull(message = "Quantity is required")
    @jakarta.validation.constraints.Min(value = 1, message = "Quantity must be at least 1")
    @Column(nullable = false)
    private Integer quantity; // in kg or units

    @jakarta.validation.constraints.NotNull(message = "Price per unit is required")
    @jakarta.validation.constraints.DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Column(nullable = false)
    private BigDecimal pricePerUnit;

    @ManyToOne
    @JoinColumn(name = "farmer_id", nullable = false)
    private User farmer;
}
