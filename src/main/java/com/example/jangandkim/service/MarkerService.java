package com.example.jangandkim.service;

import com.example.jangandkim.entity.Marker;
import com.example.jangandkim.repository.MarkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarkerService {


    @Autowired
    private MarkerRepository markerRepository;

    public List<Marker> searchMarkers(String query) {
        return markerRepository.searchMarkers(query);
    }

    public List<Marker> getAllMarkers() {
        return markerRepository.findAll();
    }

    public Marker saveMarker(Marker marker) {
        return markerRepository.save(marker);
    }

    public void deleteMarkerById(Long id) {
        markerRepository.deleteById(id);
    }
}
