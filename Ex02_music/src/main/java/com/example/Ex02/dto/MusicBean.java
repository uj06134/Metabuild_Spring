package com.example.Ex02.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class MusicBean {
    private String title;
    private String singer;
    private int price;

    public MusicBean(){
        System.out.println("MusicBean()");
    }

/*    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }*/
}
