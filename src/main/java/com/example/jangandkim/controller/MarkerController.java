package com.example.jangandkim.controller;

import com.example.jangandkim.entity.Marker;
import com.example.jangandkim.service.MarkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/markers")
@CrossOrigin(origins = "*")
public class MarkerController {
    
    // DTO 클래스 정의
    static class MarkerDTO {
        private Long id;
        private double lat;
        private double lng;
        private Integer parkingLotId;

        public MarkerDTO(Long id, double lat, double lng, Integer parkingLotId) {
            this.id = id;
            this.lat = lat;
            this.lng = lng;
            this.parkingLotId = parkingLotId;
        }

        // getter/setter
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public double getLat() { return lat; }
        public void setLat(double lat) { this.lat = lat; }
        public double getLng() { return lng; }
        public void setLng(double lng) { this.lng = lng; }
        public Integer getParkingLotId() { return parkingLotId; }
        public void setParkingLotId(Integer parkingLotId) { this.parkingLotId = parkingLotId; }
    }

    private final MarkerService markerService;

    @Autowired
    public MarkerController(MarkerService markerService) {
        this.markerService = markerService;
    }

    // Marker를 MarkerDTO로 변환하는 유틸리티 메서드
    private MarkerDTO convertToDTO(Marker marker) {
        return new MarkerDTO(
            marker.getId(),
            marker.getLat(),
            marker.getLng(),
            marker.getParkingLot() != null ? marker.getParkingLot().getParkingLotID() : null
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<MarkerDTO>> searchMarkersByArea(
            @RequestParam double minLat,
            @RequestParam double maxLat,
            @RequestParam double minLng,
            @RequestParam double maxLng) {
        try {
            List<MarkerDTO> results = markerService.searchMarkersByArea(minLat, maxLat, minLng, maxLng)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
            return ResponseEntity.ok(results);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<MarkerDTO>> getAllMarkers() {
        try {
            List<MarkerDTO> markers = markerService.getAllMarkers()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
            return ResponseEntity.ok(markers);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

  @PostMapping
public ResponseEntity<MarkerDTO> saveMarker(@RequestBody Marker marker) {
    try {
        Marker savedMarker = markerService.saveMarker(marker);
        return ResponseEntity.ok(convertToDTO(savedMarker));
    } catch (DataIntegrityViolationException e) {
        // 중복 키 등 데이터 무결성 위반 예외
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .build();
    } catch (IllegalArgumentException e) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .build();
    } catch (Exception e) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .build();
    }
}

@DeleteMapping("/api/markers/{id}")
public ResponseEntity<?> deleteMarker(@PathVariable Long id) {
    markerService.deleteMarkerById(id); // 이 로직이 올바르게 작동하는지 확인 필요
    return ResponseEntity.noContent().build();
}

}