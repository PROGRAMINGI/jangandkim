package com.example.jangandkim.repository;

import com.example.jangandkim.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;


@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    // 기본적인 CRUD 작업만 필요
    Optional<Sensor> findBysensorID(int sensorID);
    
    boolean existsBysensorID(int sensorID);
    
    // status 검색도 유지
    List<Sensor> findBystatus(String status);
}