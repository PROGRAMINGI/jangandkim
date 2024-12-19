package com.example.jangandkim.controller;

import com.example.jangandkim.dto.SensorDataDTO;
import com.example.jangandkim.entity.Sensor;
import com.example.jangandkim.entity.SensorData;
import com.example.jangandkim.service.SensorDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensor-data")
public class SensorDataController {

    @Autowired
    private SensorDataService sensorDataService;

    @GetMapping
    public ResponseEntity<List<SensorData>> getAllSensorData() {
        return ResponseEntity.ok(sensorDataService.getAllSensorData());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensorData> getSensorDataById(@PathVariable int id) {
        SensorData sensorData = sensorDataService.getSensorDataById(id);
        if (sensorData != null) {
            return ResponseEntity.ok(sensorData);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<SensorData> createSensorData(@RequestBody SensorDataDTO sensorDataDTO) {
        Sensor sensor = new Sensor();
        sensor.setSensorID(sensorDataDTO.getSensorID());

        SensorData sensorData = new SensorData();
        sensorData.setSensor(sensor);
        sensorData.setDistance(sensorDataDTO.getDistance());
        sensorData.setTimestamp(sensorDataDTO.getTimestamp());

        return ResponseEntity.ok(sensorDataService.saveSensorData(sensorData));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SensorData> updateSensorData(@PathVariable int id, @RequestBody SensorDataDTO sensorDataDTO) {
        Sensor sensor = new Sensor();
        sensor.setSensorID(sensorDataDTO.getSensorID());

        SensorData sensorData = new SensorData();
        sensorData.setSensor(sensor);
        sensorData.setDistance(sensorDataDTO.getDistance());
        sensorData.setTimestamp(sensorDataDTO.getTimestamp());

        SensorData updated = sensorDataService.updateSensorData(id, sensorData);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSensorData(@PathVariable int id) {
        sensorDataService.deleteSensorData(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{sensorId}")
    public ResponseEntity<String> getSensorStatus(@PathVariable int sensorId) {
        Sensor sensor = new Sensor();
        sensor.setSensorID(sensorId);
        
        boolean isOccupied = sensorDataService.checkSensorStatus(sensor);
        return ResponseEntity.ok(isOccupied ? "Occupied" : "Empty");
    }
}