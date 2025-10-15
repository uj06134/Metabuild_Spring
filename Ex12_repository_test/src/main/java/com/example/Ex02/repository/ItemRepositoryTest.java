package com.example.Ex02.repository;

import com.example.Ex02.entity.EntityTest;
import com.example.Ex02.entity.ItemEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("1개 레코드 삽입")
    public void insert(){
        ItemEntity item = new ItemEntity();
        // itemEntity.setId(1L);
        item.setItemNm("아이템1");
        item.setPrice(3000);
        item.setItemDetail("아이템 설명1");
        item.setRegTime(LocalDateTime.now());
        itemRepository.save(item);
    }

    @Test
    @DisplayName("10개 레코드 삽입")
    public void insert10(){
        String[] fruit = {"사과", "배", "오렌지"};
        String[] description = {"달아요", "맛있어요", "맛없어요", "떫어요"};
        int[] price = {111, 222, 333, 444, 555};

        for(int i=1;i<=10;i++){
            ItemEntity item = new ItemEntity();
            item.setItemNm(fruit[i % fruit.length]);
            item.setPrice(price[i % price.length]);
            item.setItemDetail(description[i % description.length]);
            item.setRegTime(LocalDateTime.now());
            itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("모든 상품 조회")
    public void getAll(){
        List<ItemEntity> itemLists = itemRepository.findAll();
        for (ItemEntity item : itemLists){
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("item_name 조회")
    public void getItem(){
        List<ItemEntity> itemLists = itemRepository.findByItemNm("오렌지");
        for (ItemEntity item : itemLists){
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("id 조회")
    public void getId(){
        Optional<ItemEntity> oid = itemRepository.findById(3L); // findById는 @Id를 가져옴
        System.out.println("oid: " + oid);
    }

    @Test
    @DisplayName("price 조회")
    public void getPrice(){
        // Query Method
        // price 300보다 작은 레코드를 가격기준 내림차순 정렬 조회
        List<ItemEntity> itemLists = itemRepository.findByPriceLessThanOrderByPriceDesc(300);
        for (ItemEntity item : itemLists){
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("reg_time 조회")
    public void getReg_time(){
        LocalDateTime standardDate = LocalDateTime.of(2025, 1, 1, 0, 0, 0);
        // 25년도 이후
        List<ItemEntity> itemLists = itemRepository.findByRegTimeGreaterThan(standardDate);
        for (ItemEntity item : itemLists){
            System.out.println(item);
        }
    }

    // 쿼리 어노테이션
    @Test
    @DisplayName("상품 상세설명에 '어'가 포함되고 가격이 300 이상인 레코드 조회")
    public void selectItemDetailPrice(){
        List<ItemEntity> itemLists = itemRepository.findByItemDetailPrice("어", 300);
        for (ItemEntity item : itemLists){
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("1번 레코드 삭제")
    public void deleteId(){
        itemRepository.deleteById(1L);
    }

    @Test
    @DisplayName("상품이름이 '배'인 레코드 삭제")
    public void deleteItemName(){
        itemRepository.deleteByItemName("배");
    }
}
