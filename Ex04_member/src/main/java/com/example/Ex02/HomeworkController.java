package com.example.Ex02;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeworkController {
    @GetMapping (value="/")
    public String home() {
        return "home";
    }

    @GetMapping("/memberForm")
    public String memberForm() {
        return "member/memberForm";
        // resources/templates/member/memberForm.html
    }

    // memberForm.html에서 input1요청 => member/result1.html
    // 3가지 받아서 각각 model속성, 각각 request속성 설정
    @PostMapping("/input1")
    public String input1(HttpServletRequest request, Model model) {
        String name = request.getParameter("name");
        String age = request.getParameter("age");
        String[] hobbyarr = request.getParameterValues("hobby");

        model.addAttribute("mname",name);
        request.setAttribute("rname",name);

        model.addAttribute("mage",age);
        request.setAttribute("rage",age);

        model.addAttribute("mhobby",hobbyarr);
        request.setAttribute("rhobby",hobbyarr);

        return "member/result1";
    }

    // memberForm.html에서 input2요청=> member/result2.html
    // 3가지 @RequestParam
    // bean으로 묶고 bean을 model 속성 설정
    @PostMapping("/input2")
    public String input2(@RequestParam("name") String name,
                         @RequestParam("age") String age,
                         @RequestParam("hobby") List<String> hobby,
                         Model model) {

        MemberBean mb = new MemberBean();
        mb.setName(name);
        mb.setAge(age);
        mb.setHobby(hobby);

        model.addAttribute("mb", mb);
        return "member/result2";
    }
    // memberForm.html에서 input3요청=> member/result3.html
    // 3가지 받아서 ModelAndView
    @PostMapping("/input3")
    public ModelAndView input3(HttpServletRequest request) {

        String name = request.getParameter("name");
        String age = request.getParameter("age");
        String[] hobbyarr = request.getParameterValues("hobby");

        ModelAndView mav = new ModelAndView();

        mav.addObject("name", name);
        mav.addObject("age", age);
        mav.addObject("hobbyarr", hobbyarr);

        mav.setViewName("member/result3");
        return mav;
    }

    // memberForm.html에서 input4요청=> member/result4.html
    // 3가지 받아서 command 객체로 만들어서 보내기
    @PostMapping("/input4")
    public String input4(MemberBean mb) {
        return "member/result4";
    }

    // memberForm.html에서 input5요청=> member/result5.html
    // 3가지 받아서 command 객체로 만들어서 별칭(@ModelAttribute) 설정해서 보내기
    @PostMapping("/input5")
    public String input5(@ModelAttribute("mb") MemberBean mb) {
        return "member/result4";
    }
}
