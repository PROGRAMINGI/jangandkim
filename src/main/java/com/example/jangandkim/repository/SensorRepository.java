package com.example.jangandkim.repository;

import com.example.jangandkim.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;
import java.util.Timer;
@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    // 특정 주차장의 모든 센서 찾기
    List<Sensor> findByParkingLotId(int parkingLotId);
    
    // 센서 ID로 센서 찾기 (Optional 반환)
    Optional<Sensor> findBySensorId(int sensorId);
    
    // 특정 상태의 센서들 찾기 (ACTIVE, INACTIVE 등)
    List<Sensor> findByStatus(String status);
    

    
    // 특정 주차장의 활성화된 센서 수 계산

    
    // 센서 ID가 존재하는지 확인
    boolean existsBySensorId(int sensorId);
    
    // 특정 주차장의 센서들 삭제
  
}