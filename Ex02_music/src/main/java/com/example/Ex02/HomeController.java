package com.example.Ex02;

import com.example.Ex02.dto.MusicBean;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    // http://localhost:9292/
    @RequestMapping(value= "/", method = RequestMethod.GET)
    public String home(){
        return "home";
    }

    @RequestMapping(value= "/form", method = RequestMethod.GET)
    // @GetMapping(value="/form")
    public String form(){
        return "form";
    }

    @RequestMapping(value= "/input1", method = RequestMethod.POST)
    // @PostMapping(value="/input1")
    public String input1(HttpServletRequest request, Model model){

        String title = request.getParameter("title");
        String singer = request.getParameter("singer");
        String price = request.getParameter("price");
        System.out.println(title+"/"+singer + "/"+price);

        request.setAttribute("rtitle",title);
        model.addAttribute("mtitle",title);

        request.setAttribute("rsinger",singer);
        model.addAttribute("msinger",singer);

        request.setAttribute("rprice",price);
        model.addAttribute("mprice",price);
        return "music/result1";
    }

    @RequestMapping(value= "/input2", method = RequestMethod.POST)
    // @PostMapping(value="/input2")
    public String input2(@RequestParam("title")String title,
                         @RequestParam("singer")String singer,
                         @RequestParam("price")int price,
                         Model model) {

        System.out.println(title + ", " + singer + ", " + price);

        MusicBean mb = new MusicBean();
        mb.setTitle(title);
        mb.setSinger(singer);
        mb.setPrice(price);
        model.addAttribute("mb", mb);

        return "music/result2";
    }

    @PostMapping(value="/input3")
    public ModelAndView input3(HttpServletRequest request){
        String title = request.getParameter("title");
        String singer = request.getParameter("singer");
        String price = request.getParameter("price");

        ModelAndView mav = new ModelAndView();
        mav.addObject("title",title);
        mav.addObject("singer",singer);
        mav.addObject("price",price);
        mav.addObject("addr","서울");

        MusicBean mb = new MusicBean();
        mb.setTitle(title);
        mb.setSinger(singer);
        mb.setPrice(Integer.parseInt(price));
        mav.addObject("mavMusic",mb);

        mav.setViewName("music/result3");
        return mav;
    }

    @PostMapping("/input4")
    public String input4(MusicBean mb) {
        // MusicBean mb: command 객체(아래 8줄의 의미를 갖고있음)
        /*String title = request.getParameter("title");
        String singer = request.getParameter("singer");
        String price = request.getParameter("price");
        MusicBean mb = new MusicBean();
        mb.setTitle(title);
        mb.setSinger(singer);
        mb.setPrice(Integer.parseInt(price));
        mav.addObject("musicBean",mb);*/  //소문자 시작: musicBean

        return "music/result4";
    }
    @PostMapping("/input5")
    public String input5(@ModelAttribute("bean") MusicBean mb) { //ModelAttribute(): 별칭 지정

        return "music/result5";
    }
}
