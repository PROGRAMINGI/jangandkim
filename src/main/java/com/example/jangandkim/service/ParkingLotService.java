package com.example.jangandkim.service;

import com.example.jangandkim.entity.ParkingLot;
import com.example.jangandkim.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingLotService {
    @Autowired
    private ParkingLotRepository parkingLotRepository;

    public ParkingLotService(ParkingLotRepository parkingLotRepository) {
        this.parkingLotRepository = parkingLotRepository;
    }

    public List<ParkingLot> getAllParkingLots() {
        return parkingLotRepository.findAll();
    }

    public ParkingLot getParkingLotById(int id) {
        return parkingLotRepository.findById(id).orElse(null);
    }

    public ParkingLot getParkingLotByName(String name) {
        return parkingLotRepository.findByName(name).orElse(null);
    }

    public ParkingLot saveParkingLot(ParkingLot parkingLot) {
        if (parkingLotRepository.existsById(parkingLot.getParkingLotID())) {
            throw new RuntimeException("주차장 ID " + parkingLot.getParkingLotID() + "가 이미 존재합니다.");
        }
        return parkingLotRepository.save(parkingLot);
    }

    public ParkingLot updateParkingLot(int id, ParkingLot parkingLot) {
        ParkingLot existing = getParkingLotById(id);
        if (existing != null) {
            existing.setName(parkingLot.getName());
            return parkingLotRepository.save(existing);
        }
        return null;
    }

    public ParkingLot updateParkingLotByName(String name, ParkingLot parkingLot) {
        ParkingLot existing = getParkingLotByName(name);
        if (existing != null) {
            existing.setName(parkingLot.getName());
            return parkingLotRepository.save(existing);
        }
        return null;
    }

    public void deleteParkingLot(int id) {
        parkingLotRepository.deleteById(id);
    }
}
