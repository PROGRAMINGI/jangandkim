package com.example.jangandkim.controller;

import com.example.jangandkim.entity.Favorite;
import com.example.jangandkim.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
 
@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    // 모든 즐겨찾기 조회
    @GetMapping
    public ResponseEntity<List<Favorite>> getAllFavorites() {
        List<Favorite> favorites = favoriteService.getAllFavorites();
        return ResponseEntity.ok(favorites);
    }

    // ID로 특정 즐겨찾기 조회
    @GetMapping("/{id}")
    public ResponseEntity<Favorite> getFavoriteById(@PathVariable int id) {
        Favorite favorite = favoriteService.getFavoriteById(id);
        if (favorite != null) {
            return ResponseEntity.ok(favorite);
        }
        return ResponseEntity.notFound().build();
    }

    // 새 즐겨찾기 추가
    @PostMapping
    public ResponseEntity<Favorite> addFavorite(@RequestBody Favorite favorite) {
        Favorite savedFavorite = favoriteService.saveFavorite(favorite);
        return ResponseEntity.ok(savedFavorite);
    }

    // 특정 즐겨찾기 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable int id) {
        favoriteService.deleteFavorite(id);
        return ResponseEntity.noContent().build();
    }
}
