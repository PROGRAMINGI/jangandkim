package com.example.jangandkim.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name = "parkingspace")
public class ParkingSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spaceID")
    private int spaceID;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "parkingLotID", nullable = false)
    private ParkingLot parkingLot;

    @Column(name = "spaceLocation", length = 50, nullable = false)
    private String spaceLocation;

    @Column(name = "spaceNumber", length = 50, nullable = false)
    private String spaceNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ParkingStatus status;

    
    @JsonManagedReference
    @OneToOne
    @JoinColumn(name = "sensorID", unique = true, nullable = true)
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

    public String getSpaceNumber() {
        return spaceNumber;
    }

    public void setSpaceNumber(String spaceNumber) {
        this.spaceNumber = spaceNumber;
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
