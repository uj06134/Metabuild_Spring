package com.example.Ex02.repository;

import com.example.Ex02.entity.EntityTest;
import com.example.Ex02.entity.ItemEntity;
import jakarta.transaction.Transactional;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    List<ItemEntity> findByItemNm(String 오렌지);
    List<ItemEntity> findByPriceLessThanOrderByPriceDesc(int i);

    List<ItemEntity> findByRegTimeGreaterThan(LocalDateTime standardDate);

    // @Query(value = "select * from item_entity where item_detail like %:keyword% and price >= :price", nativeQuery = true)
    @Query(value = "select i from ItemEntity i where i.itemDetail like %:keyword% and i.price >= :price")
    List<ItemEntity> findByItemDetailPrice(@Param("keyword") String keyword, int price);

    @Modifying
    @Transactional
    @Query("DELETE FROM ItemEntity i WHERE i.itemNm = :keyword")
    void deleteByItemName(@Param("keyword") String keyword);
}
