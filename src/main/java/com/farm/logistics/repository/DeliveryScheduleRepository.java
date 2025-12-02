package com.farm.logistics.repository;

import com.farm.logistics.model.DeliverySchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryScheduleRepository extends JpaRepository<DeliverySchedule, Long> {
    Optional<DeliverySchedule> findByShipmentId(Long shipmentId);
}
