package com.example.Ex02.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Arrays;
import java.util.List;

public class MemberDto {
    @NotBlank(message = "아이디는 필수입니다.")
    private String id;
    @NotBlank(message = "이름은 필수입니다.")
    private String name;
    @NotBlank(message = "비번은 필수입니다.")
    private String password;
    @NotBlank(message = "성별을 선택해주세요.")
    private String gender;
    @NotEmpty(message = "하나 이상의 취미를 선택해야 합니다.")
    private List<String> hobby;
    @NotBlank(message = "주소는 필수입니다.")
    private String address;
    private int mpoint;

    private String hobbyAsString;

    public String getHobbyAsString() {
        return (hobby != null) ? String.join(",",hobby):null;
    }

    public void setHobbyAsString(String hobbyAsString) {
        this.hobby = Arrays.asList(hobbyAsString.split(","));
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<String> getHobby() {
        return hobby;
    }

    public void setHobby(List<String> hobby) {
        this.hobby = hobby;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMpoint() {
        return mpoint;
    }

    public void setMpoint(int mpoint) {
        this.mpoint = mpoint;
    }
}
