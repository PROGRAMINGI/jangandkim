package com.example.jangandkim.service;

import com.example.jangandkim.entity.ParkingSpace;
import com.example.jangandkim.entity.ParkingLot;
import com.example.jangandkim.repository.ParkingSpaceRepository;
import com.example.jangandkim.repository.ParkingLotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingSpaceService {

    private final ParkingSpaceRepository parkingSpaceRepository;
    private final ParkingLotRepository parkingLotRepository;

    public ParkingSpaceService(ParkingSpaceRepository parkingSpaceRepository, ParkingLotRepository parkingLotRepository) {
        this.parkingSpaceRepository = parkingSpaceRepository;
        this.parkingLotRepository = parkingLotRepository;
    }

    // 모든 주차 공간 저장
    public List<ParkingSpace> saveAllParkingSpaces(List<ParkingSpace> parkingSpaces) {
        for (ParkingSpace space : parkingSpaces) {
            validateAndSetParkingLot(space);
        }
        return parkingSpaceRepository.saveAll(parkingSpaces);
    }

    // 단일 주차 공간 저장
    public ParkingSpace saveParkingSpace(ParkingSpace parkingSpace) {
        validateAndSetParkingLot(parkingSpace);
        return parkingSpaceRepository.save(parkingSpace);
    }

    // 특정 ParkingLot의 모든 주차 공간 조회
    public List<ParkingSpace> getParkingSpacesByParkingLot(ParkingLot parkingLot) {
        return parkingSpaceRepository.findByParkingLot(parkingLot);
    }

    // 모든 주차 공간 조회
    public List<ParkingSpace> getAllParkingSpaces() {
        return parkingSpaceRepository.findAll();
    }

    // 특정 ID로 주차 공간 조회
    public ParkingSpace getParkingSpaceById(int id) {
        return parkingSpaceRepository.findById(id).orElse(null);
    }

    // 주차 공간 업데이트
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

    // 주차 공간 삭제
    public void deleteParkingSpace(int id) {
        try {
            parkingSpaceRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("ID가 " + id + "인 주차 공간 삭제 중 오류 발생: " + e.getMessage(), e);
        }
    }

    // 주차 공간 유효성 검사 및 ParkingLot 설정
    private void validateAndSetParkingLot(ParkingSpace parkingSpace) {
        if (parkingSpace.getParkingLot() == null || parkingSpace.getParkingLot().getParkingLotID() == 0) {
            throw new IllegalArgumentException("유효하지 않은 ParkingLotID입니다.");
        }

        ParkingLot lot = parkingLotRepository.findById(parkingSpace.getParkingLot().getParkingLotID())
                .orElseThrow(() -> new IllegalArgumentException("ID가 " + parkingSpace.getParkingLot().getParkingLotID() + "인 주차장을 찾을 수 없습니다."));
        parkingSpace.setParkingLot(lot);

        if (parkingSpace.getSpaceLocation() == null || !parkingSpace.getSpaceLocation().matches("\\d+,\\d+")) {
            throw new IllegalArgumentException("유효하지 않은 spaceLocation 형식입니다: " + parkingSpace.getSpaceLocation());
        }
    }
}
