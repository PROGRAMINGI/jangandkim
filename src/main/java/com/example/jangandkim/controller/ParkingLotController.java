package com.example.jangandkim.controller;

import com.example.jangandkim.entity.ParkingLot;
import com.example.jangandkim.entity.ParkingSpace;
import com.example.jangandkim.entity.Sensor;
import com.example.jangandkim.repository.SensorRepository;
import com.example.jangandkim.service.ParkingLotService;
import com.example.jangandkim.service.ParkingSpaceService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import javax.print.DocFlavor.STRING;

@RestController
@RequestMapping("/api/parking-lots")
public class ParkingLotController {

    @Autowired
    private ParkingLotService parkingLotService;

    @Autowired
    private ParkingSpaceService parkingSpaceService;
    
    @Autowired
    private SensorRepository sensorRepository;
    
    // 모든 주차장 조회
    @GetMapping
    public ResponseEntity<List<ParkingLot>> getAllParkingLots() {
        return ResponseEntity.ok(parkingLotService.getAllParkingLots());
    }

    // 특정 ID로 주차장 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getParkingLotById(@PathVariable int id) {
        ParkingLot parkingLot = parkingLotService.getParkingLotById(id);
        if (parkingLot != null) {
            return ResponseEntity.ok(parkingLot);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(Map.of("error", "주차장을 찾을 수 없습니다.", "id", id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getParkingLotByName(@PathVariable("name") String name) {
        ParkingLot parkingLot = parkingLotService.getParkingLotByName(name);
        if (parkingLot != null) {
            return ResponseEntity.ok(parkingLot);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(Map.of("error", "주차장을 찾을 수 없습니다.", "이름:", name));
    }

    // 주차장 생성
    @PostMapping
    public ResponseEntity<?> createParkingLot(@RequestBody ParkingLot parkingLot) {
        try {
            // 클라이언트 요청에서 ParkingLotID는 전달되지 않아야 함
            ParkingLot savedParkingLot = parkingLotService.saveParkingLot(parkingLot);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedParkingLot);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(Map.of("error", "주차장 생성 실패", "message", e.getMessage()));
        }
    }
    

    // 주차장 정보 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<?> updateParkingLot(@PathVariable int id, @RequestBody ParkingLot parkingLot) {
        try {
            ParkingLot updatedParkingLot = parkingLotService.updateParkingLot(id, parkingLot);
            return ResponseEntity.ok(updatedParkingLot);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(Map.of("error", "주차장 업데이트 실패", "id", id, "message", e.getMessage()));
        }
    }

    // 주차장 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteParkingLot(@PathVariable int id) {
        try {
            parkingLotService.deleteParkingLot(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(Map.of("error", "주차장 삭제 실패", "id", id, "message", e.getMessage()));
        }
    }

  // 주차 공간 저장에서 `ParkingLotID` 검증 추가
 @PostMapping("{id}/parking-spaces")
public ResponseEntity<?> saveParkingSpaces(@PathVariable int id, @RequestBody List<ParkingSpace> parkingSpaces) {
    try {
        ParkingLot parkingLot = parkingLotService.getParkingLotById(id);
        if (parkingLot == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                               .body(Map.of("error", "주차장을 찾을 수 없습니다.", "id", id));
        }

        for (ParkingSpace space : parkingSpaces) {
            space.setParkingLot(parkingLot); // 객체 연관 관계를 설정
            
            // Sensor 연결 로직 추가
            if (space.getSensor() != null && space.getSensor() != null) {
                Sensor sensor = sensorRepository.findBysensorID(space.getSensor().getSensorID())
                    .orElseThrow(() -> new EntityNotFoundException("Sensor not found: " + space.getSensor().getSensorID()));
                space.setSensor(sensor);
            }
        }

        List<ParkingSpace> savedSpaces = parkingSpaceService.saveAllParkingSpaces(parkingSpaces);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSpaces);
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                           .body(Map.of("error", "주차 공간 저장 실패", "message", e.getMessage()));
    }
}

@PutMapping("/{id}/parking-spaces")
public ResponseEntity<?> updateParkingSpacesByParkingLotID(@PathVariable int id, @RequestBody List<ParkingSpace> parkingSpaces) {
    try {
        ParkingLot parkingLot = parkingLotService.getParkingLotById(id);
        if (parkingLot == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                               .body(Map.of("error", "주차장을 찾을 수 없습니다.", "id", id));
        }

        for (ParkingSpace space : parkingSpaces) {
            // Sensor 연결 로직 추가d
            if (space.getSensor() != null && space.getSensor() != null) {
                Sensor sensor = sensorRepository.findBysensorID(space.getSensor().getSensorID())
                    .orElseThrow(() -> new EntityNotFoundException("Sensor not found: " + space.getSensor().getSensorID()));
                space.setSensor(sensor);
            }

            ParkingSpace existingSpace = parkingSpaceService.getParkingSpacesByParkingLotId(id)
                                                           .stream()
                                                           .filter(s -> s.getSpaceLocation().equals(space.getSpaceLocation()))
                                                           .findFirst()
                                                           .orElse(null);
            if (existingSpace != null) {
                existingSpace.setSensor(space.getSensor());
                existingSpace.setStatus(space.getStatus());
                parkingSpaceService.saveParkingSpace(existingSpace);
            } else {
                space.setParkingLot(parkingLot);
                parkingSpaceService.saveParkingSpace(space);
            }
        }

        return ResponseEntity.ok("주차 공간 데이터가 성공적으로 업데이트되었습니다.");
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                           .body(Map.of("error", "주차 공간 업데이트 실패", "message", e.getMessage()));
    }
}

    // 특정 주차장에 속한 모든 주차 공간 조회
    @GetMapping("/{id}/parking-spaces")
    public ResponseEntity<?> getParkingSpacesByParkingLotId(@PathVariable int id) {
        try {
            ParkingLot parkingLot = parkingLotService.getParkingLotById(id);
            if (parkingLot == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body(Map.of("error", "주차장을 찾을 수 없습니다.", "id", id));
            }

            List<ParkingSpace> parkingSpaces = parkingSpaceService.getParkingSpacesByParkingLotId(id);
            return ResponseEntity.ok(parkingSpaces);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(Map.of("error", "주차 공간 조회 실패", "message", e.getMessage()));
        }
    }
}
