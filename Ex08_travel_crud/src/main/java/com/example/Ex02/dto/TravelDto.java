package com.example.Ex02.dto;

import jakarta.validation.constraints.*;

import java.util.Arrays;
import java.util.List;

public class TravelDto {
    private int num;

    @NotBlank(message="이름은 필수 입력 사항입니다.")
    private String name;

    @Min(value=1, message="1살 이상 입력해야 합니다.")
    @Max(value=100, message="100살 이하로 입력해야 합니다.")
    private int age = 1;

    @NotEmpty(message="관심지역은 1개 이상")
    private List<String> area;

    @NotEmpty(message = "원하는 여행 타입을 선택해주세요.")
    private String style;

    @NotBlank(message = "원하는 가격대를 선택해주세요.")
    private String price;

    public String areaAsString;

    public String getAreaAsString() {
        return (area != null) ? String.join(",",area):null;
    }

    public void setAreaAsString(String areaAsString) {
        this.area = Arrays.asList(areaAsString.split(","));
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getArea() {
        return area;
    }

    public void setArea(List<String> area) {
        this.area = area;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
