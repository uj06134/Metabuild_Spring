package com.example.Ex02.controller;

import com.example.Ex02.dto.HotelDto;
import com.example.Ex02.mapper.HotelMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HotelController {
    @Autowired
    private HotelMapper hotelMapper;

    @GetMapping(value = "/list")
    public String list(Model model,
                       @RequestParam(value = "whatColumn", required = false) String whatColumn,
                       @RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "page", defaultValue = "1") int page) {
        int limit = 5;
        int offset = (page - 1) * limit;

        Map<String, Object> params = new HashMap<>();
        params.put("whatColumn", whatColumn);
        params.put("keyword", keyword);
        params.put("offset", offset);
        params.put("limit", limit);

        // 실제 데이터 목록 조회
        List<HotelDto> lists = hotelMapper.selectAll(params);

        // 전체 데이터 개수 가져오기
        int totalCount = hotelMapper.getCount(params);

        // 전체 페이지 수 계산
        int totalPage = (int) Math.ceil((double) totalCount / limit);

        model.addAttribute("lists", lists);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("whatColumn", whatColumn);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);

        return "list";
    }

    @GetMapping(value = "/insert")
    public String insertForm(@ModelAttribute("hDto") HotelDto hDto) {
        return "insert";
    }

    @PostMapping(value = "/insertProc")
    public String insertProc(@ModelAttribute("hDto") @Valid HotelDto hDto, BindingResult br) {
        if (br.hasErrors()) {
            return "insert";
        }
        hotelMapper.insertHotel(hDto);
        return "redirect:/list";
    }

    @GetMapping("/update")
    public String update(@RequestParam("num") int num, Model model,
                         @RequestParam(value = "whatColumn",required = false) String whatColumn,
                         @RequestParam(value = "keyword",required = false) String keyword,
                         @RequestParam(value = "page",defaultValue = "1") int page) {

        model.addAttribute("whatColumn",whatColumn);
        model.addAttribute("keyword",keyword);
        model.addAttribute("page",page);

        HotelDto hDto = hotelMapper.findByNum(num);
        model.addAttribute("hDto", hDto);
        model.addAttribute("servicesList", List.of("조식", "스파", "픽업", "라운지"));
        model.addAttribute("roomTypeList", List.of("스탠다드", "디럭스", "스위트"));
        model.addAttribute("paymentList", List.of("카드", "계좌이체", "현금"));
        return "update";
    }

    @PostMapping("/updateProc")
    public String updateProc(@ModelAttribute("hDto") @Valid HotelDto hDto, BindingResult br, Model model,
                             @RequestParam(value = "whatColumn",required = false) String whatColumn,
                             @RequestParam(value = "keyword",required = false) String keyword,
                             @RequestParam(value = "page",defaultValue = "1") int page) {

        model.addAttribute("whatColumn",whatColumn);
        model.addAttribute("keyword",keyword);
        model.addAttribute("page",page);

        model.addAttribute("servicesList", List.of("조식", "스파", "픽업", "라운지"));
        model.addAttribute("roomTypeList", List.of("스탠다드", "디럭스", "스위트"));
        model.addAttribute("paymentList", List.of("카드", "계좌이체", "현금"));
        if (br.hasErrors()) {
            return "update";
        }
        hotelMapper.updateHotel(hDto);
        String encodeKeyword = keyword != null ? URLEncoder.encode(keyword, StandardCharsets.UTF_8) : "";
        return "redirect:/list?page=" + page + "&whatColumn=" + whatColumn + "&keyword=" + encodeKeyword;

    }

    @GetMapping(value = "/delete")
    public String delete(@RequestParam("num") int num, Model model,
                         @RequestParam(value = "whatColumn",required = false) String whatColumn,
                         @RequestParam(value = "keyword",required = false) String keyword,
                         @RequestParam(value = "page",defaultValue = "1") int page) {

        hotelMapper.deleteHotel(num);

        int limit = 5;
        int offset = (page - 1) * limit;
        Map<String, Object> params = new HashMap<>();
        params.put("whatColumn", whatColumn);
        params.put("keyword", keyword);
        params.put("offset", offset);
        params.put("limit", limit);

        int totalCount = hotelMapper.getCount(params);
        if(totalCount % limit == 0) {
            page = page - 1;
        }

        model.addAttribute("whatColumn",whatColumn);
        model.addAttribute("keyword",keyword);
        model.addAttribute("page",page);

        String encodeKeyword = keyword != null ? URLEncoder.encode(keyword, StandardCharsets.UTF_8) : "";
        return "redirect:/list?page="+page+"&whatColumn="+whatColumn+"&keyword="+encodeKeyword;
    }

}
