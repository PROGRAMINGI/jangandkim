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
    @Column(name = "ParkingLotID", nullable = false)
    private int parkingLotID;

    @Column(name = "SpaceLocation", length = 50, nullable = false)
    private String spaceLocation;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    private ParkingStatus status;

    @ManyToOne
    @Column(name = "SensorID", nullable = true) // NULL 허용 명시
    private int sensorID;

    @Column(name = "SpaceNumber", length = 50, nullable = false)
    private String spaceNumber;

    // Getters and Setters
    public int getSpaceID() {
        return spaceID;
    }

    public void setSpaceID(int spaceID) {
        this.spaceID = spaceID;
    }

    public int getParkingLotID() {
        return parkingLotID;
    }

    public void setParkingLot(int parkingLotID) {
        this.parkingLotID = parkingLotID;
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

    public int getSensorID() {
        return sensorID;
    }

    public void setSensor(int sensorID) {
        this.sensorID = sensorID;
    }

    public String getSpaceNumber() {
        return spaceNumber;
    }

    public void setSpaceNumber(String spaceNumber) {
        this.spaceNumber = spaceNumber;
    }
}
