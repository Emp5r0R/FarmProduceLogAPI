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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type; // e.g., Vegetable, Fruit, Grain

    @Column(nullable = false)
    private Integer quantity; // in kg or units

    @Column(nullable = false)
    private BigDecimal pricePerUnit;

    @ManyToOne
    @JoinColumn(name = "farmer_id", nullable = false)
    private User farmer;
}
