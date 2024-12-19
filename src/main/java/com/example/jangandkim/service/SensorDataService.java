package com.example.jangandkim.service;

import com.example.jangandkim.entity.ParkingSpace;
import com.example.jangandkim.entity.ParkingStatus;
import com.example.jangandkim.entity.Sensor;
import com.example.jangandkim.entity.SensorData;
import com.example.jangandkim.repository.SensorDataRepository;
import com.example.jangandkim.repository.ParkingSpaceRepository;
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
    @Autowired
    private ParkingSpaceRepository parkingSpaceRepository;
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

    float maxDistance = recentData.stream()
        .map(SensorData::getDistance)
        .max(Float::compareTo)
        .orElse(0f);
    
    float minDistance = recentData.stream()
        .map(SensorData::getDistance)
        .min(Float::compareTo)
        .orElse(0f);

    float distanceChange = maxDistance - minDistance;

    // ParkingSpace에서 현재 상태 가져오기
    ParkingSpace space = parkingSpaceRepository.findBySensorSensorID(sensor.getSensorID());
    if (space != null) {
        String currentStatus = space.getStatus().toString();
        
        // 현재 AVAILABLE이고 거리 변화가 크면 OCCUPIED로
        if ("AVAILABLE".equals(currentStatus) && distanceChange >= 10) {
            space.setStatus(ParkingStatus.OCCUPIED);
            parkingSpaceRepository.save(space);
            return "OCCUPIED";
        }
        
        // 현재 OCCUPIED이고 거리 변화가 크면 AVAILABLE로
        if ("OCCUPIED".equals(currentStatus) && distanceChange >= 10) {
            space.setStatus(ParkingStatus.AVAILABLE);
            parkingSpaceRepository.save(space);
            return "AVAILABLE";
        }
        
        // 변화가 없으면 현재 상태 유지
        return currentStatus;
    }

    return "AVAILABLE";  // 기본값
}
    // 오래된 데이터 정리
    @Transactional
    public void cleanupOldData(Sensor sensor) {
        sensorDataRepository.deleteOldDataBySensor(sensor.getSensorID());
    }
}