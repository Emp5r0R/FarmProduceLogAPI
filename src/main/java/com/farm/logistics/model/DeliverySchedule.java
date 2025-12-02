package com.farm.logistics.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "delivery_schedules")
@Data
@NoArgsConstructor
public class DeliverySchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "shipment_id", nullable = false)
    private Shipment shipment;

    @Column(nullable = false)
    private String vehicleNumber;

    @Column(nullable = false)
    private String driverName;

    @Column(nullable = false)
    private String driverContact;

    private LocalDateTime scheduledPickupTime;
    private LocalDateTime estimatedDeliveryTime;
}
