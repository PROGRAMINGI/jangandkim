package com.example.jangandkim.repository;

import com.example.jangandkim.entity.ParkingLot;
import com.example.jangandkim.entity.ParkingSpace;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Integer> {

    // Sensor가 NULL인 주차 공간 조회
    List<ParkingSpace> findByParkingLotID(int parkingLotID);  // parkingLotID 필드를 직접 사용

    // Sensor 관련 메서드는 유지
    List<ParkingSpace> findBySensorIsNull();
//
}