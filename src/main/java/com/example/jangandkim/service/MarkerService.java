package com.example.jangandkim.service;

import com.example.jangandkim.entity.Marker;
import com.example.jangandkim.repository.MarkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarkerService {
    private final MarkerRepository markerRepository;

    @Autowired
    public MarkerService(MarkerRepository markerRepository) {
        this.markerRepository = markerRepository;
    }

    public List<Marker> searchMarkers(String query) {
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("검색어는 비어있을 수 없습니다.");
        }
        return markerRepository.searchMarkers(query.trim());
    }

    public List<Marker> getAllMarkers() {
        return markerRepository.findAll();
    }

    public Marker saveMarker(Marker marker) {
        if (marker == null) {
            throw new IllegalArgumentException("마커 데이터는 null일 수 없습니다.");
        }
        // 필수 필드 검증
        if (marker.getLat() == 0.0 || marker.getLng() == 0.0) {
            throw new IllegalArgumentException("위도와 경도는 필수입니다.");
        }
        return markerRepository.save(marker);
    }

    public void deleteMarkerById(Long id) {
        if (!markerRepository.existsById(id)) {
            throw new RuntimeException("ID " + id + "인 마커를 찾을 수 없습니다.");
        }
        markerRepository.deleteById(id);
    }
}
