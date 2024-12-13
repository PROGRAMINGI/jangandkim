package com.example.jangandkim.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import java.util.List;
//
@Entity
@Table(name = "parkinglot")
public class ParkingLot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ParkingLotID")
    private int parkingLotID;

    @Column(name = "name", length = 100, nullable = false)
    @NotNull(message = "주차장 이름은 필수입니다.")
    @Size(min = 1, max = 100, message = "주차장 이름은 1자 이상, 100자 이하로 입력해야 합니다.")
    private String name;

    @OneToMany(mappedBy = "parkingLot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParkingSpace> parkingSpaces;
    
    @OneToOne(mappedBy = "parkingLot", cascade = CascadeType.ALL) // Marker와의 1:1 연결
    private Marker marker;

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

    public List<ParkingSpace> getParkingSpaces() {
        return parkingSpaces;
    }

    public void setParkingSpaces(List<ParkingSpace> parkingSpaces) {
        this.parkingSpaces = parkingSpaces;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }
}