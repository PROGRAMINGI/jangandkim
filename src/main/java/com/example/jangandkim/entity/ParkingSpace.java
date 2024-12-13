package com.example.jangandkim.entity;

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
    private ParkingLot parkingLot;

    @Column(name = "SpaceLocation", length = 50, nullable = false)
    private String spaceLocation;

    @Column(name = "SpaceNumber", length = 50, nullable = false)
    private String spaceNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    private ParkingStatus status;

    @OneToOne
    @JoinColumn(name = "SensorID", unique = true)
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
