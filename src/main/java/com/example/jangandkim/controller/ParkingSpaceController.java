package com.example.jangandkim.controller;

import com.example.jangandkim.entity.ParkingLot;
import com.example.jangandkim.entity.ParkingSpace;
import com.example.jangandkim.entity.ParkingStatus;
import com.example.jangandkim.repository.ParkingLotRepository;
import com.example.jangandkim.repository.ParkingSpaceRepository;
import com.example.jangandkim.service.ParkingSpaceService;
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
public ResponseEntity<List<ParkingSpace>> createParkingSpaces(@RequestBody List<ParkingSpace> parkingSpaces) {
    List<ParkingSpace> savedSpaces = parkingSpaceService.saveAllParkingSpaces(parkingSpaces);
    return ResponseEntity.ok(savedSpaces);
}


@PutMapping
public ResponseEntity<List<ParkingSpace>> updateParkingSpaces(@RequestBody List<ParkingSpace> parkingSpaces) {
    List<ParkingSpace> updatedSpaces = parkingSpaceService.saveAllParkingSpaces(parkingSpaces);
    return ResponseEntity.ok(updatedSpaces);
}

@GetMapping
public ResponseEntity<List<ParkingSpace>> getAllParkingSpaces( @RequestParam(required = true) Integer parkingLotID) {
    try {
        List<ParkingSpace> spaces = parkingSpaceService.getParkingSpacesByParkingLotId(parkingLotID);
        return ResponseEntity.ok(spaces);
    } catch (Exception e) {
        return ResponseEntity.internalServerError().build();
    }
}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParkingSpace(@PathVariable int id) {
        parkingSpaceService.deleteParkingSpace(id);
        return ResponseEntity.noContent().build();
    }
}
