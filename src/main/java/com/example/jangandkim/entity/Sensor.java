package com.example.jangandkim.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "sensor")
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SensorID")
    private int sensorID;

    @Column(name = "SensorName", length = 50, nullable = false)
    private String sensorName;

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL)
    private List<ParkingSpace> parkingSpaces;

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL)
    private List<SensorData> sensorDataList;

    // Getters and Setters
    public int getSensorID() {
        return sensorID;
    }

    public void setSensorID(int sensorID) {
        this.sensorID = sensorID;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public List<ParkingSpace> getParkingSpaces() {
        return parkingSpaces;
    }

    public void setParkingSpaces(List<ParkingSpace> parkingSpaces) {
        this.parkingSpaces = parkingSpaces;
    }

    public List<SensorData> getSensorDataList() {
        return sensorDataList;
    }

    public void setSensorDataList(List<SensorData> sensorDataList) {
        this.sensorDataList = sensorDataList;
    }
}
