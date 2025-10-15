package com.example.Ex02;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import java.util.List;

public class ProductBean {
    @NotEmpty(message = "id 입력누락")
    private String id;
    @Length(min = 3, max=5, message = "3~5자리 이하로 입력하세요")
    @NotBlank(message = "pw 공백 안됨")
    private String pw;
    @NotEmpty(message = "product 입력누락")
    private List<String> product;
    @NotEmpty(message = "time 입력누락")
    private String time;
    @NotEmpty(message = "approve 입력누락")
    private String approve;
    @NotEmpty(message = "agree 입력누락")
    private String agree;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public List<String> getProduct() {
        return product;
    }

    public void setProduct(List<String> product) {
        this.product = product;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getApprove() {
        return approve;
    }

    public void setApprove(String approve) {
        this.approve = approve;
    }

    public String getAgree() {
        return agree;
    }

    public void setAgree(String agree) {
        this.agree = agree;
    }
}