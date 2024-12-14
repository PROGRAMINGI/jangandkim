package com.example.jangandkim.service;

import com.example.jangandkim.entity.Marker;
import com.example.jangandkim.repository.MarkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class MarkerService {
    private final MarkerRepository markerRepository;

    @Autowired
    public MarkerService(MarkerRepository markerRepository) {
        this.markerRepository = markerRepository;
    }

    // searchMarkers 메서드 삭제 또는 다른 검색 로직으로 변경
    // 예: 영역 기반 검색으로 변경
    public List<Marker> searchMarkersByArea(double minLat, double maxLat, double minLng, double maxLng) {
        return markerRepository.findMarkersInArea(minLat, maxLat, minLng, maxLng);
    }

    public List<Marker> getAllMarkers() {
        return markerRepository.findAll();
    }

    public Marker saveMarker(Marker marker) {
        if (marker == null) {
            throw new IllegalArgumentException("마커 데이터는 null일 수 없습니다.");
        }
        if (marker.getLat() == 0.0 || marker.getLng() == 0.0) {
            throw new IllegalArgumentException("위도와 경도는 필수입니다.");
        }
        return markerRepository.save(marker);
    }

    @Transactional
public void deleteMarkerById(Long id) {
    Marker marker = markerRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("ID " + id + "인 마커를 찾을 수 없습니다."));
        
    // ParkingLot 관계 해제
    if (marker.getParkingLot() != null) {
        marker.setParkingLot(null);
    }
    
    markerRepository.delete(marker);
    markerRepository.flush();  // 명시적으로 flush 호출
}
}