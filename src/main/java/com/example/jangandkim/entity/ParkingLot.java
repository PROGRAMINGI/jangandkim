package com.example.jangandkim.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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


    @Column(nullable = true) 
    private int version;
    
   @Column(name = "name", length = 100, nullable = false)
    @NotNull(message = "주차장 이름은 필수입니다.")
@Size(min = 1, max = 100, message = "주차장 이름은 1자 이상, 100자 이하로 입력해야 합니다.")
private String name;

@Column(name = "location", length = 255, nullable = false)
@NotNull(message = "주차장 위치는 필수입니다.")
@Size(min = 1, max = 255, message = "주차장 위치는 1자 이상, 255자 이하로 입력해야 합니다.")
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
