package com.example.Ex02;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public class BookBean {
    @NotEmpty(message="제목 입력 누락")
    private String title;
    @NotBlank(message="저자 입력 누락") // 공백 입력
    private String author;
    @NotNull(message="가격 입력 누락") // 불가능
    @Pattern(regexp = "^[0-9]+$",message = "숫자로 입력") // "^[0-9]+$": 숫자가 1개 이상
    private String price;
    @Length(min = 3, message = "3글자 이상 입력")
    private String publisher;

    @NotEmpty(message="서점 입력 누락")
    private List<String> bookstore;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public List<String> getBookstore() {
        return bookstore;
    }

    public void setBookstore(List<String> bookstore) {
        this.bookstore = bookstore;
    }
}
