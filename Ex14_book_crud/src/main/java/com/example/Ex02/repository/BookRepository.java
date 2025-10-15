package com.example.Ex02.repository;

import com.example.Ex02.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer> {
    Page<BookEntity> findByTitleContainingOrAuthorContaining(String keyword, String keyword1, Pageable pageable);
}
