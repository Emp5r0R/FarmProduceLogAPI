package com.farm.logistics.controller;

import com.farm.logistics.model.DeliverySchedule;
import com.farm.logistics.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {
    @Autowired
    private DeliveryService deliveryService;

    @PostMapping("/schedule/{shipmentId}")
    @PreAuthorize("hasRole('DISTRIBUTOR') or hasRole('ADMIN')")
    public ResponseEntity<DeliverySchedule> scheduleDelivery(@PathVariable Long shipmentId, @RequestBody DeliverySchedule schedule) {
        return ResponseEntity.ok(deliveryService.scheduleDelivery(shipmentId, schedule));
    }

    @GetMapping("/{shipmentId}")
    public ResponseEntity<DeliverySchedule> getDeliveryDetails(@PathVariable Long shipmentId) {
        return ResponseEntity.ok(deliveryService.getDeliveryDetails(shipmentId));
    }
}
