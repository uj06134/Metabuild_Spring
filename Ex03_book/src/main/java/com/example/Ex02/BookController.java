package com.example.Ex02;

import com.example.Ex02.BookBean;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookController {
    @GetMapping("/bookform")
    public String form(@ModelAttribute("bb") BookBean bb) {
        return "book/form";
    }

    @PostMapping("/bookProc")
    public String formProc(@ModelAttribute("bb") @Valid BookBean bb, BindingResult br) { // @valid: 유효성 검사, BindingResult: 검사 결과
        String page;
        System.out.println("br.hasErrors(): " + br.hasErrors());

        if (br.hasErrors()) {
            page = "book/form";
        } else {
            page = "book/result";
        }
        return page;
    }
}
