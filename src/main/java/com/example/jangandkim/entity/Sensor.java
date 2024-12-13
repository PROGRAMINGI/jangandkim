package com.example.jangandkim.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
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

    @OneToOne(mappedBy = "sensor", cascade = CascadeType.ALL)
    private ParkingSpace parkingSpace;

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL)
    private List<SensorData> sensorDataList = new ArrayList<>();

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

    public ParkingSpace getParkingSpace() {
        return parkingSpace;
    }

    public void setParkingSpace(ParkingSpace parkingSpaces) {
        this.parkingSpace = parkingSpaces;
    }

    public List<SensorData> getSensorDataList() {
        return sensorDataList;
    }

    public void setSensorDataList(List<SensorData> sensorDataList) {
        this.sensorDataList = sensorDataList;
    }
}