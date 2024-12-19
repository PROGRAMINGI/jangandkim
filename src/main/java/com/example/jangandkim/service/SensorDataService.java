package com.example.jangandkim.service;

import com.example.jangandkim.entity.Sensor;
import com.example.jangandkim.entity.SensorData;
import com.example.jangandkim.repository.SensorDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.time.Duration;
import java.time.Instant;

@Service
public class SensorDataService {

    @Autowired
    private SensorDataRepository sensorDataRepository;

    // 모든 센서 데이터 조회
    public List<SensorData> getAllSensorData() {
        return sensorDataRepository.findAll();
    }

    // ID로 특정 센서 데이터 조회
    public SensorData getSensorDataById(int id) {
        return sensorDataRepository.findById(id).orElse(null);
    }

    // 센서 데이터 저장 (자동으로 오래된 데이터 정리)
    @Transactional
    public SensorData saveSensorData(SensorData sensorData) {
        SensorData saved = sensorDataRepository.save(sensorData);
        cleanupOldData(sensorData.getSensor());
        return saved;
    }

    // 센서 데이터 수정
    @Transactional
    public SensorData updateSensorData(int id, SensorData sensorData) {
        return sensorDataRepository.findById(id)
            .map(existing -> {
                existing.setDistance(sensorData.getDistance());
                existing.setTimestamp(sensorData.getTimestamp());
                existing.setSensor(sensorData.getSensor());
                return sensorDataRepository.save(existing);
            })
            .orElse(null);
    }

    // 센서 데이터 삭제
    @Transactional
    public void deleteSensorData(int id) {
        sensorDataRepository.deleteById(id);
    }

    // 특정 센서의 최신 데이터 조회
    public SensorData getLatestDataBySensor(Sensor sensor) {
        return sensorDataRepository.findTopBySensorOrderByTimestampDesc(sensor);
    }

    // 센서 상태 확인 (최근 5개 데이터 기반)
    public String checkSensorStatus(Sensor sensor) {
        List<SensorData> recentData = sensorDataRepository.findTop5BySensorOrderByTimestampDesc(sensor);
        
        if (recentData.size() < 5) {
            return "AVAILABLE";
        }
    
        // 최근 5개 데이터의 시간 간격 확인
        LocalDateTime latestTime = recentData.get(0).getTimestamp();
        LocalDateTime oldestTime = recentData.get(4).getTimestamp();
        Duration duration = Duration.between(oldestTime, latestTime);
    
        // 5초 이내의 데이터만 사용
        if (duration.getSeconds() <= 5) {
            float maxDistance = recentData.stream()
                .map(SensorData::getDistance)
                .max(Float::compareTo)
                .orElse(0f);
            
            float minDistance = recentData.stream()
                .map(SensorData::getDistance)
                .min(Float::compareTo)
                .orElse(0f);
    
            float distanceChange = maxDistance - minDistance;
    
            if (distanceChange >= 10) {
                return "OCCUPIED";
            }
        }
    
        // 기존 상태 유지
        return "AVAILABLE";  // 또는 ParkingSpace의 현재 상태를 반환
    }
    // 오래된 데이터 정리
    @Transactional
    public void cleanupOldData(Sensor sensor) {
        sensorDataRepository.deleteOldDataBySensor(sensor.getSensorID());
    }
}