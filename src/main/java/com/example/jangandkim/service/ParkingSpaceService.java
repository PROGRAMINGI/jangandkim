package com.example.jangandkim.service;

import com.example.jangandkim.entity.ParkingSpace;
import com.example.jangandkim.entity.Sensor;
import com.example.jangandkim.repository.ParkingSpaceRepository;
import com.example.jangandkim.repository.SensorRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingSpaceService {

    private final ParkingSpaceRepository parkingSpaceRepository;
    private final SensorRepository sensorRepository;

    public ParkingSpaceService(ParkingSpaceRepository parkingSpaceRepository, SensorRepository sensorRepository) {
        this.parkingSpaceRepository = parkingSpaceRepository;
        this.sensorRepository = sensorRepository;
    }


    public List<ParkingSpace> saveAllParkingSpaces(List<ParkingSpace> parkingSpaces) {
        // Sensor 유효성 검증 및 처리
        parkingSpaces.forEach(space -> {
            if (space.getSensor() != null) {
                Integer sensorID = space.getSensor().getSensorID();
                Sensor existingSensor = sensorRepository.findBysensorID(sensorID).orElse(null);
                
                if (existingSensor != null) {
                    space.setSensor(existingSensor);
                } else {
                    System.out.println("Warning: 센서 ID " + sensorID + "가 존재하지 않습니다. null로 설정됩니다.");
                    space.setSensor(null);
                }
            }
        });
        
        return parkingSpaceRepository.saveAll(parkingSpaces);
    }

    public ParkingSpace saveParkingSpace(ParkingSpace parkingSpace) {
        return parkingSpaceRepository.save(parkingSpace);
    }

    // ParkingLotID로 조회하는 메서드 수정
    public List<ParkingSpace> getParkingSpacesByParkingLotId(int parkingLotId) {
        return parkingSpaceRepository.findByParkingLot_ParkingLotID(parkingLotId);
    }

    // 나머지 메서드들은 그대로 유지
    public List<ParkingSpace> getParkingSpacesWithoutSensor() {
        return parkingSpaceRepository.findBySensorIsNull();
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
        if (parkingSpace.getParkingLot() != null) {
            existing.setParkingLot(parkingSpace.getParkingLot());
        }

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