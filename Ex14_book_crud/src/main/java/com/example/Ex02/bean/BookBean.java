package com.example.Ex02.bean;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BookBean {

    private int no;
    @NotBlank(message = "제목을 입력하세요")
    private String title;
    @NotBlank(message = "저자를 입력하세요")
    private String author;
    @NotBlank(message = "출판사를 입력하세요")
    private String publisher;
    @Min(value=1, message="가격은 1원 이상입니다")
    private int price;
    @NotBlank(message = "입고일을 선택하세요")
    private String buy;
    @NotBlank(message = "배송비를 선택하세요")
    private String kind;
    @NotBlank(message = "구입가능 서점을 선택하세요")
    private String bookstore;
    @Min(value = 1, message = "보유수량은 1개 이상이어야 합니다")
    private int count;

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getBookstore() {
        return bookstore;
    }

    public void setBookstore(String bookstore) {
        this.bookstore = bookstore;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
