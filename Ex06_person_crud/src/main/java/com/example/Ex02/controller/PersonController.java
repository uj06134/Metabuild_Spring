package com.example.Ex02.controller;
import com.example.Ex02.dto.PersonDto;
import com.example.Ex02.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PersonController {
    // Spring이 PersonMapper 타입의 Bean을 자동 주입
    @Autowired
    private PersonMapper personMapper;

    @GetMapping(value = "/form")
    public String form() {

        return "form";
    }
    // form.html 입력 -> submit(insertProc)
    @PostMapping(value = "/insertProc")
    public String insertProc(PersonDto pDto){
        personMapper.insertPerson(pDto);
        // return "list"; // list.html 이동
        return "redirect:/list"; // redirect 요청(get방식)
    }
    @GetMapping(value = "/list")
    public String list(Model model){
        List<PersonDto> lists = personMapper.selectAll();
        model.addAttribute("lists",lists);
        return "list";
    }
    // 상세조회
    @GetMapping(value = "content_view")
    public String content_view(@RequestParam("num") int num, Model model){
        PersonDto pDto = personMapper.findByNum(num);
        model.addAttribute("pDto",pDto);
        return "content_view";
    }

    // 수정폼 띄우기
    @GetMapping("/modify")
    public String modifyForm(@RequestParam("num") int num, Model model) {
        PersonDto pDto = personMapper.findByNum(num);
        model.addAttribute("pDto", pDto);
        return "modify";
    }

    // 수정 처리
    @PostMapping("/modify")
    public String modify(PersonDto pDto){
        int cnt = personMapper.updatePerson(pDto);
        System.out.println("modify cnt: " + cnt);
        return "redirect:/list";
    }

    @GetMapping (value = "/delete")
    public String delete(@RequestParam("num")int num){
        int cnt = personMapper.deletePerson(num);
        System.out.println("delete cnt:" + cnt);
        return "redirect:/list";
    }


}
