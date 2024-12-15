package com.example.jangandkim.controller;

import com.example.jangandkim.entity.Marker;
import com.example.jangandkim.service.MarkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
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
        private Integer parkingLotID;

        public MarkerDTO(Long id, double lat, double lng, Integer parkingLotID) {
            this.id = id;
            this.lat = lat;
            this.lng = lng;
            this.parkingLotID = parkingLotID;
        }

        // getter/setter
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public double getLat() { return lat; }
        public void setLat(double lat) { this.lat = lat; }
        public double getLng() { return lng; }
        public void setLng(double lng) { this.lng = lng; }
        public Integer getParkingLotId() { return parkingLotID; }
        public void setParkingLotId(Integer parkingLotId) { this.parkingLotID = parkingLotID; }
    }

    // API 응답을 위한 DTO
    static class ApiResponse {
        private boolean success;
        private String message;
        private Object data;

        public ApiResponse(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public ApiResponse(boolean success, String message, Object data) {
            this.success = success;
            this.message = message;
            this.data = data;
        }

        // getter/setter
        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public Object getData() { return data; }
        public void setData(Object data) { this.data = data; }
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

    //마커 여러개

    @GetMapping("/search/{parkingLotID}")
public ResponseEntity<ApiResponse> searchByParkingLotId(@PathVariable Integer parkingLotID) {
    try {
        List<Marker> markers = markerService.findAllByParkingLotId(parkingLotID);
        if (!markers.isEmpty()) {
            List<MarkerDTO> markerDTOs = markers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse(true, "마커 검색 성공", markerDTOs));
        }
        return ResponseEntity.notFound().build();
    } catch (Exception e) {
        return ResponseEntity.internalServerError()
            .body(new ApiResponse(false, "서버 오류가 발생했습니다."));
    }
}





    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchMarkersByArea(
            @RequestParam double minLat,
            @RequestParam double maxLat,
            @RequestParam double minLng,
            @RequestParam double maxLng) {
        try {
            List<MarkerDTO> results = markerService.searchMarkersByArea(minLat, maxLat, minLng, maxLng)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse(true, "마커 검색 성공", results));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "잘못된 검색 범위입니다."));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ApiResponse(false, "서버 오류가 발생했습니다."));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllMarkers() {
        try {
            List<MarkerDTO> markers = markerService.getAllMarkers()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse(true, "모든 마커 조회 성공", markers));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ApiResponse(false, "서버 오류가 발생했습니다."));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> saveMarker(@RequestBody Marker marker) {
        try {
            Marker savedMarker = markerService.saveMarker(marker);
            return ResponseEntity.ok(new ApiResponse(true, "마커 저장 성공", convertToDTO(savedMarker)));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiResponse(false, "중복된 주차장 ID가 존재합니다."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, "서버 오류가 발생했습니다."));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteMarker(@PathVariable Long id) {
        try {
            markerService.deleteMarkerById(id);
            return ResponseEntity.ok(new ApiResponse(true, "마커가 성공적으로 삭제되었습니다."));
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse(false, "삭제할 마커를 찾을 수 없습니다."));
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, "마커 삭제 중 오류가 발생했습니다."));
        }
    }
}