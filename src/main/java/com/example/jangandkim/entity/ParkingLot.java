package com.example.jangandkim.entity;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "parkinglot")
public class ParkingLot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ParkingLotID")
    @JsonProperty("parkingLotID") // JSON에서 "parkingLotID" 키를 매핑
    private int parkingLotID;


    @Version
    private int version; // 낙관적 잠금에 사용할 버전 필드
    
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "location", length = 255, nullable = false)
    private String location;

    @OneToMany(mappedBy = "parkingLot", cascade = CascadeType.ALL)
    private List<ParkingSpace> parkingSpaces;

    // Getters and Setters
    public int getParkingLotID() {
        return parkingLotID;
    }

    public void setParkingLotID(int parkingLotID) {
        this.parkingLotID = parkingLotID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<ParkingSpace> getParkingSpaces() {
        return parkingSpaces;
    }

    public void setParkingSpaces(List<ParkingSpace> parkingSpaces) {
        this.parkingSpaces = parkingSpaces;
    }
}
