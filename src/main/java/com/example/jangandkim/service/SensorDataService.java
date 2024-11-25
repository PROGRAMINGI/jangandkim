package com.example.jangandkim.service;

import com.example.jangandkim.entity.Sensor;
import com.example.jangandkim.entity.SensorData;
import com.example.jangandkim.repository.SensorDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorDataService {

    @Autowired
    private SensorDataRepository sensorDataRepository;

    public List<SensorData> getAllSensorData() {
        return sensorDataRepository.findAll();
    }

    public SensorData getSensorDataById(int id) {
        return sensorDataRepository.findById(id).orElse(null);
    }

    public SensorData saveSensorData(SensorData sensorData) {
        return sensorDataRepository.save(sensorData);
    }

    public SensorData updateSensorData(int id, SensorData sensorData) {
        SensorData existing = getSensorDataById(id);
        if (existing != null) {
            existing.setDistance(sensorData.getDistance());
            existing.setTimestamp(sensorData.getTimestamp());
            existing.setSensor(sensorData.getSensor());
            return sensorDataRepository.save(existing);
        }
        return null;
    }

    public void deleteSensorData(int id) {
        sensorDataRepository.deleteById(id);
    }

    // 특정 센서의 최신 데이터 조회
    public SensorData getLatestDataBySensor(Sensor sensor) {
        return sensorDataRepository.findTopBySensorOrderByTimestampDesc(sensor);
    }
}
