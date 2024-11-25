package com.example.jangandkim.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

@Entity
@Table(name = "parkingspace")
public class ParkingSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SpaceID")
    private int spaceID;

    @ManyToOne
    @JoinColumn(name = "ParkingLotID", nullable = false)
    @JsonProperty("parkingLot") // JSON에서 "parkingLot" 키를 매핑
    private ParkingLot parkingLot;

    @Column(name = "SpaceLocation", length = 50, nullable = false)
    private String spaceLocation;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    private ParkingStatus status;

    @ManyToOne
    @JoinColumn(name = "SensorID", nullable = true) // NULL 허용 명시
    private Sensor sensor;

    // Getters and Setters
    public int getSpaceID() {
        return spaceID;
    }

    public void setSpaceID(int spaceID) {
        this.spaceID = spaceID;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public String getSpaceLocation() {
        return spaceLocation;
    }

    public void setSpaceLocation(String spaceLocation) {
        this.spaceLocation = spaceLocation;
    }

    public ParkingStatus getStatus() {
        return status;
    }

    public void setStatus(ParkingStatus status) {
        this.status = status;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
