package com.example.Ex02.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class HotelDto {
    private int num;

    @NotBlank(message="이름을 입력하세요")
    private String name;
    @NotEmpty(message="서비스를 1개 이상 선택해주세요")
    private List<String> services;
    @NotEmpty(message="객실 타입을 선택해주세요")
    private String room_type;
    @NotEmpty(message="결제방식을 선택해주세요")
    private String payment;
    @NotBlank(message="체크인 날짜를 선택해주세요")
    private String checkin_date;
    @NotNull(message="숙박일수를 입력하세요")
    @Min(value=1, message="숙박일수는 1일 이상이어야 합니다")
    private int nights;
    private String servicesAsString;

    public String getServicesAsString() {
        return (services != null) ? String.join(",", services) : null;
    }

    public void setServicesAsString(String servicesAsString) {
        this.services = Arrays.asList(servicesAsString.split(","));
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

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getCheckin_date() {
        return checkin_date;
    }

    public void setCheckin_date(String  checkin_date) {
        this.checkin_date = checkin_date;
    }

    public int getNights() {
        return nights;
    }

    public void setNights(int nights) {
        this.nights = nights;
    }
}
