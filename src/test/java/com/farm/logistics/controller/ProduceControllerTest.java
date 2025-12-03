package com.farm.logistics.controller;

import com.farm.logistics.exception.ResourceNotFoundException;
import com.farm.logistics.model.Produce;
import com.farm.logistics.service.ProduceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProduceController.class)
@AutoConfigureMockMvc(addFilters = false) // Disable security filters for simplicity in this test
public class ProduceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProduceService produceService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "farmer", roles = {"FARMER"})
    public void addProduce_InvalidInput_ReturnsBadRequest() throws Exception {
        Produce produce = new Produce();
        // Missing required fields like name, type, quantity, price

        mockMvc.perform(post("/api/produce")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produce)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Name is required"));
    }

    @Test
    @WithMockUser(username = "farmer", roles = {"FARMER"})
    public void updateProduce_NotFound_ReturnsNotFound() throws Exception {
        Produce produce = new Produce();
        produce.setName("Tomato");
        produce.setType("Vegetable");
        produce.setQuantity(100);
        produce.setPricePerUnit(new BigDecimal("2.5"));

        when(produceService.updateProduce(eq(1L), any(Produce.class)))
                .thenThrow(new ResourceNotFoundException("Produce not found"));

        mockMvc.perform(put("/api/produce/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produce)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Produce not found"));
    }
}
