package com.example.Ex02.controller;

import com.example.Ex02.dto.ItemDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

// @Controller : 스프링 MVC 컨트롤러 클래스임을 선언 → 클라이언트 요청을 받고 뷰로 데이터를 전달하는 역할
@Controller

// @RequestMapping("/thymeleaf") : 이 클래스의 모든 요청 앞에는 /thymeleaf가 prefix처럼 붙음.
// 예: /thymeleaf/ex01, /thymeleaf/ex02 … 식으로 요청 가능
@RequestMapping(value="/thymeleaf")

public class ThymeleafController {

    // Ex01을 GET방식으로 요청했을때 아래 메서드 실행 ==> view01.HTML 실행
    @RequestMapping(value = "/ex01", method = RequestMethod.GET)
    public String Exam01(Model model){
        model.addAttribute("name","아이유");
        model.addAttribute("age",30);
        model.addAttribute("addr", "서울");
        return "view01"; // resources/templates/~~/html
    }
    // @RequestMapping(value= "/ex02", method = RequestMethod.GET)
    @GetMapping(value = "/ex02")
    public String Exam02(Model model){
        ItemDto itemDto = new ItemDto();
        itemDto.setNo(1);
        itemDto.setItemNm("상품");
        itemDto.setItemDetail("상품 설명");
        itemDto.setPrice(3000);
        model.addAttribute("itemDto",itemDto);
        return "view02";
    }
    @GetMapping(value= "/ex03")
    public String Exam03(Model model){
        List<ItemDto> lists = new ArrayList<ItemDto>();
        for(int i=0; i<=10; i++){
            ItemDto itemDto = new ItemDto();
            itemDto.setNo(1);
            itemDto.setItemNm("상품");
            itemDto.setItemDetail("상품 설명");
            itemDto.setPrice(3000);
            lists.add(itemDto);
        }
        model.addAttribute("lists",lists);
        return "view03";
    }
    @GetMapping(value= "/ex04")
    public String Exam04(Model model){

        return "thymeleaf/view04";
    }
    // http://localhost:9292/thymeleaf/ex05?param1=가나&param2=다라
    @GetMapping(value= "/ex05")
    public String Exam05(Model model, String param1, String param2){
        if(param1 == null){
            param1 = "A";
        }
        if(param2 == null){
            param2 = "B";
        }
        model.addAttribute("param1",param1);
        model.addAttribute("param2",param2);
        return "thymeleaf/view05";
    }
}