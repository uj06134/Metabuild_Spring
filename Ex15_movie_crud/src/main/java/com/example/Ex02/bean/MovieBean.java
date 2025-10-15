package com.example.Ex02.bean;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MovieBean {
    private Integer num;
    @NotBlank(message = "아이디는 필수 입력입니다")
    private String id;
    @NotBlank(message = "이름은 필수 입력입니다")
    private String name;
    @NotNull(message = "나이는 필수 입력입니다.")
    private Integer age;
    @NotBlank(message = "장르는 최소 1개 이상 선택해야 합니다.")
    private String genre;
    @NotBlank(message = "즐겨보는 시간대를 선택해야 합니다")
    private String time;
    @NotNull(message = "동반 관객수를 선택해야 합니다.")
    private Integer partner;
    private String memo;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getPartner() {
        return partner;
    }

    public void setPartner(Integer partner) {
        this.partner = partner;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
