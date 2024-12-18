package com.example.jangandkim.service;

import com.example.jangandkim.entity.Sensor;
import com.example.jangandkim.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    public List<Sensor> getAllSensors() {
        return sensorRepository.findAll();
    }

    public Sensor getSensorById(int id) {
        return sensorRepository.findById(id).orElse(null);
    }

    public Sensor saveSensor(Sensor sensor) {
        if (sensor.getSensorName() == null || sensor.getSensorName().trim().isEmpty()) {
            sensor.setSensorName("Default Sensor");
        }
        return sensorRepository.save(sensor);
    }
    
    public Sensor updateSensor(int id, Sensor sensor) {
        Sensor existing = getSensorById(id);
        if (existing != null) {
            existing.setSensorName(sensor.getSensorName());
            return sensorRepository.save(existing);
        }
        return null;
    }

    public void deleteSensor(int id) {
        sensorRepository.deleteById(id);
    }
}
