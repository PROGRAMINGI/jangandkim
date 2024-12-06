package com.example.jangandkim.controller;

import com.example.jangandkim.entity.ParkingLot;
import com.example.jangandkim.entity.ParkingSpace;
import com.example.jangandkim.service.ParkingLotService;
import com.example.jangandkim.service.ParkingSpaceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/parking-lots")
public class ParkingLotController {

    @Autowired
    private ParkingLotService parkingLotService;

    @Autowired
    private ParkingSpaceService parkingSpaceService;

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

    // 주차장 생성
    @PostMapping
    public ResponseEntity<?> createParkingLot(@RequestBody ParkingLot parkingLot) {
        try {
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

    // 특정 주차장에 주차 공간 저장
    @PostMapping("/{id}/parking-spaces")
    public ResponseEntity<?> saveParkingSpaces(@PathVariable int id, @RequestBody List<ParkingSpace> parkingSpaces) {
        try {
            ParkingLot parkingLot = parkingLotService.getParkingLotById(id);
            if (parkingLot == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body(Map.of("error", "주차장을 찾을 수 없습니다.", "id", id));
            }

            // 각 주차 공간에 ParkingLotID 설정
            for (ParkingSpace space : parkingSpaces) {
                space.setParkingLotID(parkingLot.getParkingLotID());
            }

            List<ParkingSpace> savedSpaces = parkingSpaceService.saveAllParkingSpaces(parkingSpaces);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSpaces);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(Map.of("error", "주차 공간 저장 실패", "message", e.getMessage()));
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
