package com.farm.logistics.controller;

import com.farm.logistics.model.Produce;
import com.farm.logistics.service.ProduceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produce")
public class ProduceController {
    @Autowired
    private ProduceService produceService;

    @PostMapping
    @PreAuthorize("hasRole('FARMER') or hasRole('ADMIN')")
    public ResponseEntity<Produce> addProduce(@jakarta.validation.Valid @RequestBody Produce produce) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(produceService.addProduce(produce, userDetails.getUsername()));
    }

    @GetMapping
    public ResponseEntity<List<Produce>> getAllProduce() {
        return ResponseEntity.ok(produceService.getAllProduce());
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('FARMER')")
    public ResponseEntity<List<Produce>> getMyProduce() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(produceService.getProduceByFarmer(userDetails.getUsername()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('FARMER') or hasRole('ADMIN')")
    public ResponseEntity<Produce> updateProduce(@PathVariable Long id, @jakarta.validation.Valid @RequestBody Produce produce) {
        return ResponseEntity.ok(produceService.updateProduce(id, produce));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('FARMER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteProduce(@PathVariable Long id) {
        produceService.deleteProduce(id);
        return ResponseEntity.ok().build();
    }
}
