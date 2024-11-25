package com.example.jangandkim.service;

import com.example.jangandkim.entity.Favorite;
import com.example.jangandkim.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    // 모든 즐겨찾기 목록 조회
    public List<Favorite> getAllFavorites() {
        return favoriteRepository.findAll();
    }

    // ID로 즐겨찾기 조회
    public Favorite getFavoriteById(int id) {
        return favoriteRepository.findById(id).orElse(null);
    }

    // 즐겨찾기 저장
    public Favorite saveFavorite(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }

    // 즐겨찾기 삭제
    public void deleteFavorite(int id) {
        favoriteRepository.deleteById(id);
    }
}
