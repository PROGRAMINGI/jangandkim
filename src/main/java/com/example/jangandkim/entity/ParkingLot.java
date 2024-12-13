package com.example.jangandkim.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

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

    @Column(name = "location", length = 255, nullable = false)
    @NotNull(message = "주차장 위치는 필수입니다.")
    @Size(min = 1, max = 255, message = "주차장 위치는 1자 이상, 255자 이하로 입력해야 합니다.")
    private String location;

    @Column(name = "version")
    private Integer version;

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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
