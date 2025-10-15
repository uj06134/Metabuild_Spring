package com.example.Ex02.repository;

import com.example.Ex02.entity.EntityTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntityRepository extends JpaRepository<EntityTest, Integer> {
    List<EntityTest> findByName(String name);
    List<EntityTest> findByAge(int i);
    List<EntityTest> findByAgeGreaterThan(int i);
    List<EntityTest> findByAgeGreaterThanEqual(int i);
}
