package com.example.jangandkim.controller;

import com.example.jangandkim.entity.Marker;
import com.example.jangandkim.service.MarkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/markers")
public class MarkerController {


    @Autowired
    private MarkerService markerService;

    @GetMapping("/search")
    public ResponseEntity<List<Marker>> searchMarkers(@RequestParam String query) {
        List<Marker> results = markerService.searchMarkers(query);
        return ResponseEntity.ok(results);
    }


    @GetMapping
    public ResponseEntity<List<Marker>> getAllMarkers() {
        return ResponseEntity.ok(markerService.getAllMarkers());
    }

    @PostMapping
    public ResponseEntity<Marker> saveMarker(@RequestBody Marker marker) {
        return ResponseEntity.ok(markerService.saveMarker(marker));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMarker(@PathVariable Long id) {
        markerService.deleteMarkerById(id);
        return ResponseEntity.noContent().build();
    }
}
