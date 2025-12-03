package com.farm.logistics.service;

import com.farm.logistics.exception.BadRequestException;
import com.farm.logistics.exception.ResourceNotFoundException;
import com.farm.logistics.model.Produce;
import com.farm.logistics.model.Shipment;
import com.farm.logistics.model.User;
import com.farm.logistics.repository.ProduceRepository;
import com.farm.logistics.repository.ShipmentRepository;
import com.farm.logistics.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipmentService {
    private final ShipmentRepository shipmentRepository;
    private final ProduceRepository produceRepository;
    private final UserRepository userRepository;

    @Transactional
    public Shipment createShipment(Long produceId, Integer quantity, String distributorUsername) {
        Produce produce = produceRepository.findById(produceId)
                .orElseThrow(() -> new ResourceNotFoundException("Produce not found with id: " + produceId));

        if (produce.getQuantity() < quantity) {
            throw new BadRequestException("Insufficient produce quantity. Available: " + produce.getQuantity());
        }

        User distributor = userRepository.findByUsername(distributorUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Distributor not found with username: " + distributorUsername));

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
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found with id: " + id));
        shipment.setStatus(status);
        if (status == Shipment.Status.DELIVERED) {
            shipment.setArrivalTime(LocalDateTime.now());
        }
        return shipmentRepository.save(shipment);
    }
}
