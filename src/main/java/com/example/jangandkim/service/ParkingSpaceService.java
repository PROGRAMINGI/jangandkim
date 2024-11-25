package com.example.jangandkim.service;

import com.example.jangandkim.entity.ParkingSpace;
import com.example.jangandkim.entity.ParkingStatus;
import com.example.jangandkim.entity.ParkingLot;
import com.example.jangandkim.repository.ParkingSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.jangandkim.repository.ParkingLotRepository;

import java.util.List;

@Service
public class ParkingSpaceService {

    private final ParkingSpaceRepository parkingSpaceRepository;
    private final ParkingLotRepository parkingLotRepository;

    public ParkingSpaceService(ParkingSpaceRepository parkingSpaceRepository, ParkingLotRepository parkingLotRepository) {
        this.parkingSpaceRepository = parkingSpaceRepository;
        this.parkingLotRepository = parkingLotRepository;
    }

    public void saveAllParkingSpaces(List<ParkingSpace> parkingSpaces) {
        for (ParkingSpace space : parkingSpaces) {
            validateAndSetParkingLot(space);
        }
        parkingSpaceRepository.saveAll(parkingSpaces);
    }

    public ParkingSpace saveParkingSpace(ParkingSpace parkingSpace) {
        validateAndSetParkingLot(parkingSpace);
        return parkingSpaceRepository.save(parkingSpace);
    }

    private void validateAndSetParkingLot(ParkingSpace parkingSpace) {
        if (parkingSpace.getParkingLot() == null || parkingSpace.getParkingLot().getParkingLotID() == 0) {
            throw new RuntimeException("Invalid ParkingLotID");
        }

        ParkingLot lot = parkingLotRepository.findById(parkingSpace.getParkingLot().getParkingLotID())
                .orElseThrow(() -> new RuntimeException("ParkingLot not found: " + parkingSpace.getParkingLot().getParkingLotID()));
        parkingSpace.setParkingLot(lot);

        if (parkingSpace.getSpaceLocation() == null || !parkingSpace.getSpaceLocation().matches("\\d+,\\d+")) {
            throw new RuntimeException("Invalid spaceLocation format: " + parkingSpace.getSpaceLocation());
        }
    }

    public List<ParkingSpace> getAllParkingSpaces() {
        return parkingSpaceRepository.findAll();
    }

    public ParkingSpace getParkingSpaceById(int id) {
        return parkingSpaceRepository.findById(id).orElse(null);
    }

    public ParkingSpace updateParkingSpace(int id, ParkingSpace parkingSpace) {
        ParkingSpace existing = getParkingSpaceById(id);
        if (existing == null) {
            throw new RuntimeException("ID가 " + id + "인 주차 공간을 찾을 수 없습니다.");
        }
        existing.setSpaceLocation(parkingSpace.getSpaceLocation());
        existing.setStatus(parkingSpace.getStatus());
        existing.setParkingLot(parkingSpace.getParkingLot());
        existing.setSensor(parkingSpace.getSensor());
        return parkingSpaceRepository.save(existing);
    }

    public void deleteParkingSpace(int id) {
        try {
            parkingSpaceRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("ID가 " + id + "인 주차 공간 삭제 중 오류 발생: " + e.getMessage(), e);
        }
    }
}
