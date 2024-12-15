package com.example.jangandkim.repository;

import com.example.jangandkim.entity.Marker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MarkerRepository extends JpaRepository<Marker, Long> {
    // 만약 마커 검색이 필요하다면, 실제 존재하는 필드를 사용해야 합니다
    // 예: 위치 기반 검색
    @Query("SELECT m FROM Marker m WHERE m.lat BETWEEN :minLat AND :maxLat AND m.lng BETWEEN :minLng AND :maxLng")
    List<Marker> findMarkersInArea(@Param("minLat") double minLat, @Param("maxLat") double maxLat,
                                  @Param("minLng") double minLng, @Param("maxLng") double maxLng);



      List<Marker> findAllByParkingLotParkingLotID(Integer parkingLotID);


}