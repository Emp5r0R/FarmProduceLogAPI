package com.farm.logistics.repository;

import com.farm.logistics.model.Produce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduceRepository extends JpaRepository<Produce, Long> {
    List<Produce> findByFarmerId(Long farmerId);
}
