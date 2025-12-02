package com.farm.logistics.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "shipments")
@Data
@NoArgsConstructor
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produce_id", nullable = false)
    private Produce produce;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "distributor_id", nullable = false)
    private User distributor;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    public enum Status {
        PENDING,
        IN_TRANSIT,
        DELIVERED,
        CANCELLED
    }
}
