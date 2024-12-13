package com.example.jangandkim.controller;

import com.example.jangandkim.entity.Marker;
import com.example.jangandkim.service.MarkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/markers")
@CrossOrigin(origins = "*")  // CORS 설정 추가
public class MarkerController {
    private final MarkerService markerService;

    @Autowired
    public MarkerController(MarkerService markerService) {
        this.markerService = markerService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Marker>> searchMarkers(@RequestParam String query) {
        try {
            List<Marker> results = markerService.searchMarkers(query);
            return ResponseEntity.ok(results);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Marker>> getAllMarkers() {
        try {
            return ResponseEntity.ok(markerService.getAllMarkers());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<Marker> saveMarker(@RequestBody Marker marker) {
        try {
            Marker savedMarker = markerService.saveMarker(marker);
            return ResponseEntity.ok(savedMarker);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMarker(@PathVariable Long id) {
        try {
            markerService.deleteMarkerById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}