package com.example.jangandkim.controller;
//컨트롤러
import com.example.jangandkim.entity.ParkingLot;
import com.example.jangandkim.entity.ParkingSpace;
import com.example.jangandkim.service.ParkingLotService;
import com.example.jangandkim.service.ParkingSpaceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import java.util.List;

@RestController
@RequestMapping("/api/parking-lots")
public class ParkingLotController {

    @Autowired
    private ParkingLotService parkingLotService;

    @GetMapping
    public ResponseEntity<List<ParkingLot>> getAllParkingLots() {
        return ResponseEntity.ok(parkingLotService.getAllParkingLots());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getParkingLotById(@PathVariable int id) {
        ParkingLot parkingLot = parkingLotService.getParkingLotById(id);
        if (parkingLot != null) {
            return ResponseEntity.ok(parkingLot);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body("주차장을 찾을 수 없습니다. ID: " + id);
    }


    @PostMapping
    public ResponseEntity<?> createParkingLot(@RequestBody ParkingLot parkingLot) {
        System.out.println("요청 데이터: " + parkingLot);
        try {
            ParkingLot savedLot = parkingLotService.saveParkingLot(parkingLot);
            return ResponseEntity.ok(savedLot);
        } catch (Exception e) {
            e.printStackTrace();
            // JSON 형식으로 오류 메시지 반환
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(Map.of("error", "주차장 생성 실패", "message", e.getMessage()));
        }
    }
    


    @PutMapping("/{id}")
    public ResponseEntity<?> updateParkingLot(@PathVariable int id, @RequestBody ParkingLot parkingLot) {
        ParkingLot updated = parkingLotService.updateParkingLot(id, parkingLot);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body("주차장 업데이트 실패. ID: " + id + "인 주차장이 존재하지 않습니다.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteParkingLot(@PathVariable int id) {
        try {
            parkingLotService.deleteParkingLot(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("주차장 삭제 실패. ID: " + id + "인 주차장이 존재하지 않습니다.");
        }
    }
}
