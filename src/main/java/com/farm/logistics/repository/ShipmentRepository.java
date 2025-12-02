package com.farm.logistics.repository;

import com.farm.logistics.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    List<Shipment> findByDistributorId(Long distributorId);
    List<Shipment> findByStatus(Shipment.Status status);
}
