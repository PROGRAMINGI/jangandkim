package com.example.jangandkim.controller;

import com.example.jangandkim.entity.ParkingLot;
import com.example.jangandkim.entity.ParkingSpace;
import com.example.jangandkim.entity.ParkingStatus;
import com.example.jangandkim.repository.ParkingLotRepository;
import com.example.jangandkim.repository.ParkingSpaceRepository;
import com.example.jangandkim.service.ParkingSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking-spaces")
public class ParkingSpaceController {

    private final ParkingSpaceService parkingSpaceService;

    public ParkingSpaceController(ParkingSpaceService parkingSpaceService) {
        this.parkingSpaceService = parkingSpaceService;
    }

    @PostMapping("/bulk")
    public ResponseEntity<String> saveParkingSpacesWithLot(@RequestBody List<ParkingSpace> parkingSpaces) {
        try {
            parkingSpaceService.saveAllParkingSpaces(parkingSpaces);
            return ResponseEntity.ok("주차 공간 정보가 저장되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("저장 중 오류 발생: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createParkingSpace(@RequestBody ParkingSpace parkingSpace) {
        try {
            ParkingSpace savedSpace = parkingSpaceService.saveParkingSpace(parkingSpace);
            return ResponseEntity.ok(savedSpace);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("주차 공간 생성 실패: " + e.getMessage());
        }
    }
    
    @PostMapping("/bulk")
    public ResponseEntity<?> createParkingSpaces(@RequestBody List<ParkingSpace> parkingSpaces) {
        try {
            parkingSpaceService.saveAllParkingSpaces(parkingSpaces);
            return ResponseEntity.ok("모든 주차 공간이 저장되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("주차 공간 생성 실패: " + e.getMessage());
        }
    }
    


    @GetMapping
    public ResponseEntity<List<ParkingSpace>> getAllParkingSpaces() {
        return ResponseEntity.ok(parkingSpaceService.getAllParkingSpaces());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSpace> getParkingSpaceById(@PathVariable int id) {
        ParkingSpace parkingSpace = parkingSpaceService.getParkingSpaceById(id);
        if (parkingSpace != null) {
            return ResponseEntity.ok(parkingSpace);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingSpace> updateParkingSpace(@PathVariable int id, @RequestBody ParkingSpace parkingSpace) {
        ParkingSpace updated = parkingSpaceService.updateParkingSpace(id, parkingSpace);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParkingSpace(@PathVariable int id) {
        parkingSpaceService.deleteParkingSpace(id);
        return ResponseEntity.noContent().build();
    }
}
