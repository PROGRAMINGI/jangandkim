package com.example.jangandkim.repository;

import com.example.jangandkim.entity.Marker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MarkerRepository extends JpaRepository<Marker, Long> {
    @Query("SELECT m FROM Marker m WHERE m.title LIKE %:query% OR m.spaceId LIKE %:query%")
    List<Marker> searchMarkers(@Param("query") String query);
}

