package com.example.jangandkim.dto;

import java.time.LocalDateTime;

public class SensorDataDTO {
    private int sensorID;        // 센서 ID
    private float distance;      // 거리
    private LocalDateTime timestamp;  // 타임스탬프

    // Getters and Setters
    public int getSensorID() {
        return sensorID;
    }

    public void setSensorID(int sensorID) {
        this.sensorID = sensorID;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
