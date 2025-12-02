package com.farm.logistics.service;

import com.farm.logistics.model.DeliverySchedule;
import com.farm.logistics.model.Shipment;
import com.farm.logistics.repository.DeliveryScheduleRepository;
import com.farm.logistics.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {
    @Autowired
    private DeliveryScheduleRepository deliveryScheduleRepository;

    @Autowired
    private ShipmentRepository shipmentRepository;

    public DeliverySchedule scheduleDelivery(Long shipmentId, DeliverySchedule schedule) {
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new RuntimeException("Shipment not found"));
        
        schedule.setShipment(shipment);
        return deliveryScheduleRepository.save(schedule);
    }

    public DeliverySchedule getDeliveryDetails(Long shipmentId) {
        return deliveryScheduleRepository.findByShipmentId(shipmentId)
                .orElseThrow(() -> new RuntimeException("Delivery schedule not found"));
    }
}
