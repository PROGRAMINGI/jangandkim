package com.example.jangandkim.repository;

import com.example.jangandkim.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;
import java.util.Timer;
@Repository

public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    // 특정 주차장의 모든 센서 찾기 dsfsd
    List<Sensor> findByParkingSpace_ParkingLot_ParkingLotId(int parkingLotId);
    
    Optional<Sensor> findBySensorId(int sensorId);
    
    List<Sensor> findByStatus(String status);
    
    boolean existsBySensorId(int sensorId);
}