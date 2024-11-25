package com.example.jangandkim.repository;

import com.example.jangandkim.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    // 기본 CRUD 메서드가 JpaRepository에서 제공됨
}
