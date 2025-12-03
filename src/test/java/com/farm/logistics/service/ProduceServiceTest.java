package com.farm.logistics.service;

import com.farm.logistics.exception.ResourceNotFoundException;
import com.farm.logistics.model.Produce;
import com.farm.logistics.repository.ProduceRepository;
import com.farm.logistics.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProduceServiceTest {

    @Mock
    private ProduceRepository produceRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProduceService produceService;

    @Test
    public void addProduce_UserNotFound_ThrowsException() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        Produce produce = new Produce();
        assertThrows(ResourceNotFoundException.class, () -> {
            produceService.addProduce(produce, "unknownUser");
        });
    }

    @Test
    public void updateProduce_ProduceNotFound_ThrowsException() {
        when(produceRepository.findById(1L)).thenReturn(Optional.empty());
        Produce produce = new Produce();
        assertThrows(ResourceNotFoundException.class, () -> {
            produceService.updateProduce(1L, produce);
        });
    }
}
