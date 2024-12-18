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
import java.util.Optional;
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

    @PostMapping("{id}/parking-spaces")
    public ResponseEntity<?> saveParkingSpaces(@PathVariable int id, @RequestBody List<ParkingSpace> parkingSpaces) {
        try {
            // 주차장 존재 확인
            ParkingLot parkingLot = parkingLotService.getParkingLotById(id);
            if (parkingLot == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body(Map.of("error", "주차장을 찾을 수 없습니다.", "id", id));
            }
    
            // ParkingSpace 리스트 처리
            for (ParkingSpace space : parkingSpaces) {
                space.setParkingLot(parkingLot); // 주차장 설정
                
                // Sensor 검증 및 설정
                if (space.getSensor() != null) {
                    Integer sensorID = space.getSensor().getSensorID(); // 입력된 sensorID
                    Optional<Sensor> sensorOptional = sensorRepository.findById(sensorID);
    
                    if (sensorOptional.isPresent()) {
                        space.setSensor(sensorOptional.get()); // 존재하는 센서 설정
                    } else {
                        space.setSensor(null); // 센서가 없으면 null로 설정
                    }
                } else {
                    space.setSensor(null); // 센서가 아예 입력되지 않은 경우
                }
            }
    
            // ParkingSpaces 저장
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
            // 주차장 ID로 주차장 조회
            ParkingLot parkingLot = parkingLotService.getParkingLotById(id);
            if (parkingLot == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body(Map.of("error", "주차장을 찾을 수 없습니다.", "id", id));
            }
    
            // 주차 공간 업데이트 로직
            for (ParkingSpace space : parkingSpaces) {
                // Sensor 검증 및 처리
                if (space.getSensor() != null) {
                    // SensorID 검증
                    if (space.getSensor() == null) {
                        // SensorID가 null인 경우, Sensor 정보를 제거
                        space.setSensor(null);
                    } else {
                        // Sensor 테이블에서 검색
                        Optional<Sensor> sensorOptional = sensorRepository.findById(space.getSensor().getSensorID());
                        if (sensorOptional.isEmpty()) {
                            // Sensor가 존재하지 않을 경우, null로 설정
                            space.setSensor(null);
                        } else {
                            // Sensor가 존재하면 연결
                            space.setSensor(sensorOptional.get());
                        }
                    }
                }
    
                // 기존 ParkingSpace 검색
                ParkingSpace existingSpace = parkingSpaceService.getParkingSpacesByParkingLotId(id)
                                                                 .stream()
                                                                 .filter(s -> s.getSpaceLocation().equals(space.getSpaceLocation()))
                                                                 .findFirst()
                                                                 .orElse(null);
    
                if (existingSpace != null) {
                    // 기존 공간 업데이트
                    existingSpace.setSensor(space.getSensor());
                    existingSpace.setStatus(space.getStatus());
                    parkingSpaceService.saveParkingSpace(existingSpace);
                } else {
                    // 새로운 공간 추가
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
