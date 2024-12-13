package com.example.jangandkim.repository;

import com.example.jangandkim.entity.ParkingLot;
import com.example.jangandkim.entity.ParkingSpace;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Integer> {
    // parkingLot 객체를 통해 검색
    List<ParkingSpace> findByParkingLot_ParkingLotID(int parkingLotID);
    
    // Sensor가 NULL인 주차 공간 조회
    List<ParkingSpace> findBySensorIsNull();
}