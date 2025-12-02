package com.farm.logistics.service;

import com.farm.logistics.model.Produce;
import com.farm.logistics.model.Shipment;
import com.farm.logistics.model.User;
import com.farm.logistics.repository.ProduceRepository;
import com.farm.logistics.repository.ShipmentRepository;
import com.farm.logistics.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShipmentService {
    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private ProduceRepository produceRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Shipment createShipment(Long produceId, Integer quantity, String distributorUsername) {
        Produce produce = produceRepository.findById(produceId)
                .orElseThrow(() -> new RuntimeException("Produce not found"));

        if (produce.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient produce quantity");
        }

        User distributor = userRepository.findByUsername(distributorUsername)
                .orElseThrow(() -> new RuntimeException("Distributor not found"));

        // Deduct quantity
        produce.setQuantity(produce.getQuantity() - quantity);
        produceRepository.save(produce);

        Shipment shipment = new Shipment();
        shipment.setProduce(produce);
        shipment.setQuantity(quantity);
        shipment.setDistributor(distributor);
        shipment.setStatus(Shipment.Status.PENDING);
        shipment.setDepartureTime(LocalDateTime.now());

        return shipmentRepository.save(shipment);
    }

    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }

    public Shipment updateStatus(Long id, Shipment.Status status) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found"));
        shipment.setStatus(status);
        if (status == Shipment.Status.DELIVERED) {
            shipment.setArrivalTime(LocalDateTime.now());
        }
        return shipmentRepository.save(shipment);
    }
}
