package com.example.jangandkim.controller;

import com.example.jangandkim.entity.Sensor;
import com.example.jangandkim.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    @GetMapping
    public ResponseEntity<List<Sensor>> getAllSensors() {
        return ResponseEntity.ok(sensorService.getAllSensors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sensor> getSensorById(@PathVariable int id) {
        Sensor sensor = sensorService.getSensorById(id);
        if (sensor != null) {
            return ResponseEntity.ok(sensor);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Sensor> createSensor(@RequestBody Sensor sensor) {
        if (sensor.getSensorName() == null || sensor.getSensorName().trim().isEmpty()) {
            sensor.setSensorName("Default Sensor");
        }
        Sensor savedSensor = sensorService.saveSensor(sensor);
        return ResponseEntity.ok(savedSensor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sensor> updateSensor(@PathVariable int id, @RequestBody Sensor sensor) {
        Sensor updated = sensorService.updateSensor(id, sensor);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSensor(@PathVariable int id) {
        sensorService.deleteSensor(id);
        return ResponseEntity.noContent().build();
    }
}
