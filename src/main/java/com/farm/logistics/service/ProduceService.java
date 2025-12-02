package com.farm.logistics.service;

import com.farm.logistics.model.Produce;
import com.farm.logistics.model.User;
import com.farm.logistics.repository.ProduceRepository;
import com.farm.logistics.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduceService {
    @Autowired
    private ProduceRepository produceRepository;

    @Autowired
    private UserRepository userRepository;

    public Produce addProduce(Produce produce, String username) {
        User farmer = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        produce.setFarmer(farmer);
        return produceRepository.save(produce);
    }

    public List<Produce> getAllProduce() {
        return produceRepository.findAll();
    }

    public List<Produce> getProduceByFarmer(String username) {
        User farmer = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return produceRepository.findByFarmerId(farmer.getId());
    }

    public Produce updateProduce(Long id, Produce produceDetails) {
        Produce produce = produceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produce not found"));
        
        produce.setName(produceDetails.getName());
        produce.setType(produceDetails.getType());
        produce.setQuantity(produceDetails.getQuantity());
        produce.setPricePerUnit(produceDetails.getPricePerUnit());
        
        return produceRepository.save(produce);
    }

    public void deleteProduce(Long id) {
        produceRepository.deleteById(id);
    }
}
