package com.example.Ex02.dto;

import java.util.HashMap;
import java.util.Map;

public class MyCartList {
    private Map<Integer, Integer> orderlists = null;

    public MyCartList(){
        orderlists = new HashMap<Integer, Integer>(); // 빈 장바구니 생성

    }
    public void addOrder(int pnum, int oqty){
        if(orderlists.containsKey(pnum)){ // 장바구니에 이미 상품 존재
            int oldQty = orderlists.get(pnum);
            orderlists.put(pnum, oldQty + oqty); // 기존 수량에 더하기
        } else { // 새상품
            orderlists.put(pnum,oqty);
        }
    }

    public Map<Integer, Integer> getAllOrderLists(){
        return orderlists;
    }
}
