package com.example.Ex02.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class ItemEntity { // item_entity 테이블 생성
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "itemName", nullable = false, length = 10)
    private String itemNm;
    private int price;
    private String itemDetail;
    private LocalDateTime regTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemNm() {
        return itemNm;
    }

    public void setItemNm(String itemNm) {
        this.itemNm = itemNm;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getItemDetail() {
        return itemDetail;
    }

    public void setItemDetail(String itemDetail) {
        this.itemDetail = itemDetail;
    }

    public LocalDateTime getRegTime() {
        return regTime;
    }

    public void setRegTime(LocalDateTime regTime) {
        this.regTime = regTime;
    }

    @Override
    public String toString() {
        return "ItemEntity{" +
                "id=" + id +
                ", itemNm='" + itemNm + '\'' +
                ", price=" + price +
                ", itemDetail='" + itemDetail + '\'' +
                ", regTime=" + regTime +
                '}';
    }
}
