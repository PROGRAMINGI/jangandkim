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
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/parking-spaces")
public class ParkingSpaceController {

    private final ParkingSpaceService parkingSpaceService;

    public ParkingSpaceController(ParkingSpaceService parkingSpaceService) {
        this.parkingSpaceService = parkingSpaceService;
    }
    
    @PostMapping
    public ResponseEntity<?> createParkingSpaces(@RequestBody List<ParkingSpace> parkingSpaces) {
        try {
            // 주차 공간 데이터 저장
            List<ParkingSpace> savedSpaces = parkingSpaceService.saveAllParkingSpaces(parkingSpaces);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSpaces);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(Map.of("error", "주차 공간 저장 실패", "message", e.getMessage()));
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
