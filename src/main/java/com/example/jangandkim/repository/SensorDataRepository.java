package com.example.jangandkim.repository;

import com.example.jangandkim.entity.Sensor;
import com.example.jangandkim.entity.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorDataRepository extends JpaRepository<SensorData, Integer> {
    // 특정 센서의 가장 최근 데이터 조회
    SensorData findTopBySensorOrderByTimestampDesc(Sensor sensor);
    
    // 특정 센서의 최근 5개 데이터 조회
    List<SensorData> findTop5BySensorOrderByTimestampDesc(Sensor sensor);
    
    // 센서별 오래된 데이터 삭제 (최근 10개만 유지)
    @Modifying
    @Query(value = "DELETE FROM sensordata WHERE sensorID = :sensorID " +
           "AND dataID NOT IN (SELECT dataID FROM (SELECT dataID FROM sensordata " +
           "WHERE sensorID = :sensorID ORDER BY timestamp DESC LIMIT 10) as recent)", 
           nativeQuery = true)
    void deleteOldDataBySensor(@Param("sensorID") int sensorID);
}