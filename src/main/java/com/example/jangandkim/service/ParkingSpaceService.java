package com.example.jangandkim.service;

import com.example.jangandkim.entity.ParkingSpace;
import com.example.jangandkim.repository.ParkingSpaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingSpaceService {

    private final ParkingSpaceRepository parkingSpaceRepository;

    public ParkingSpaceService(ParkingSpaceRepository parkingSpaceRepository) {
        this.parkingSpaceRepository = parkingSpaceRepository;
    }

    // 모든 주차 공간 저장
    public List<ParkingSpace> saveAllParkingSpaces(List<ParkingSpace> parkingSpaces) {
        return parkingSpaceRepository.saveAll(parkingSpaces);
    }

    // 단일 주차 공간 저장
    public ParkingSpace saveParkingSpace(ParkingSpace parkingSpace) {
        return parkingSpaceRepository.save(parkingSpace);
    }

    // 특정 ParkingLotID의 모든 주차 공간 조회
    public List<ParkingSpace> getParkingSpacesByParkingLotId(int parkingLotId) {
        return parkingSpaceRepository.findByParkingLotID(parkingLotId); 
    }

    // Sensor가 NULL인 주차 공간 조회
    public List<ParkingSpace> getParkingSpacesWithoutSensor() {
        return parkingSpaceRepository.findBySensorIsNull();
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
        
        // 기존 값 유지를 위한 null 체크 추가
        if (parkingSpace.getSpaceLocation() != null) {
            existing.setSpaceLocation(parkingSpace.getSpaceLocation());
        }
        if (parkingSpace.getStatus() != null) {
            existing.setStatus(parkingSpace.getStatus());
        }
        if (parkingSpace.getSensor() != null) {
            existing.setSensor(parkingSpace.getSensor());
        }
        if (parkingSpace.getSpaceNumber() != null) {
            existing.setSpaceNumber(parkingSpace.getSpaceNumber());
        }
        if (parkingSpace.getParkingLotID() != 0) {  // 0이 아닌 경우에만 업데이트
            existing.setParkingLotID(parkingSpace.getParkingLotID());
        }

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
}