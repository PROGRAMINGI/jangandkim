package com.example.jangandkim.repository;

import com.example.jangandkim.entity.Sensor;
import com.example.jangandkim.entity.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SensorDataRepository extends JpaRepository<SensorData, Integer> {
    // 단일 결과 반환
    SensorData findTopBySensorOrderByTimestampDesc(Sensor sensor);
}
