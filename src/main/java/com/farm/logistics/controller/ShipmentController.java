package com.farm.logistics.controller;

import com.farm.logistics.model.Shipment;
import com.farm.logistics.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {
    @Autowired
    private ShipmentService shipmentService;

    @PostMapping
    @PreAuthorize("hasRole('DISTRIBUTOR') or hasRole('ADMIN')")
    public ResponseEntity<Shipment> createShipment(@RequestBody Map<String, Object> payload) {
        Long produceId = Long.valueOf(payload.get("produceId").toString());
        Integer quantity = Integer.valueOf(payload.get("quantity").toString());
        
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        return ResponseEntity.ok(shipmentService.createShipment(produceId, quantity, userDetails.getUsername()));
    }

    @GetMapping
    public ResponseEntity<List<Shipment>> getAllShipments() {
        return ResponseEntity.ok(shipmentService.getAllShipments());
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('DISTRIBUTOR') or hasRole('ADMIN')")
    public ResponseEntity<Shipment> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        Shipment.Status status = Shipment.Status.valueOf(payload.get("status"));
        return ResponseEntity.ok(shipmentService.updateStatus(id, status));
    }
}
