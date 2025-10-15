package com.example.Ex02.repository;

import com.example.Ex02.entity.MovieEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Integer> {
    @Query("select m from MovieEntity m " +
            "where m.name like %:keyword% or m.genre like %:keyword%")
    Page<MovieEntity> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
