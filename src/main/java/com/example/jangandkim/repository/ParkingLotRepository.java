package com.example.jangandkim.repository;

import com.example.jangandkim.entity.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot, Integer> {


    Optional<ParkingLot> findByName(String name);
    
    
}
