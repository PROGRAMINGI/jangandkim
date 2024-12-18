package com.example.jangandkim.repository;

import com.example.jangandkim.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;


@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    Optional<Sensor> findBysensorID(int sensorID);
    Optional<Sensor> findBysensorName(String sensorName);
    
    boolean existsBysensorID(int sensorID);
    
    // status 관련 메서드는 제거 (Sensor 엔티티에 해당 필드가 없음)
}