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
    List<Sensor> findByparkingspace_parkinglot_parkingLotID(int parkingLotId);
    
    Optional<Sensor> findBysensorID(int sensorId);
    
    List<Sensor> findBystatus(String status);
    
    boolean existsBysensorID(int sensorId);
}