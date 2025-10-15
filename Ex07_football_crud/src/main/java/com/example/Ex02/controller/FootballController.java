package com.example.Ex02.controller;

import com.example.Ex02.dto.FootballDto;
import com.example.Ex02.mapper.FootballMapper;
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
public class FootballController {
    // FootballMapper라는 도구(인터페이스)를 스프링이 자동으로 가져와서 연결해 준다.
    // 내가 직접 new FootballMapper() 해서 만들 필요 없이,
    // 스프링이 알아서 footballMapper 변수에 준비된 객체를 넣어준다.
    @Autowired
    private FootballMapper footballMapper;

    @GetMapping(value="/write")
    public String write(@ModelAttribute("fDto") FootballDto fDto) {

        return "write";
    }

    // write.html submit클릭(writeProc요청)
    @PostMapping(value = "/writeProc")
    public String formProc(@ModelAttribute("fDto") @Valid FootballDto fDto, BindingResult br) {
        if (br.hasErrors()) {
            return "write";
        } else {
        footballMapper.insertFootball(fDto);
            int totalCount = footballMapper.getCount(new HashMap<>());
            int limit = 3;
            int totalPage = (int) Math.ceil((double) totalCount / limit);
            return "redirect:/list?page=" + totalPage;
        }
    }
    @GetMapping(value = "/list")
    public String list(Model model,
                       @RequestParam(value = "whatColumn", required = false) String whatColumn,
                       @RequestParam(value = "keyword",required = false) String keyword,
                       @RequestParam(value = "page",defaultValue = "1") int page){
        int limit = 3;
        int offset = (page - 1) * limit;

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("whatColumn", whatColumn);
        params.put("keyword", keyword);
        params.put("offset", offset);
        params.put("limit", limit);

        List<FootballDto> lists = footballMapper.selectAll(params);
        int totalCount = footballMapper.getCount(params);
        int totalPage = (int)Math.ceil((double)totalCount/limit);

        model.addAttribute("lists",lists);

        model.addAttribute("totalCount",totalCount);
        model.addAttribute("totalPage",totalPage);

        model.addAttribute("whatColumn",whatColumn);
        model.addAttribute("keyword",keyword);
        model.addAttribute("page",page);
        return "list";
    }

    // 상세조회
    @GetMapping(value = "/content_view")
    public String content_view(@RequestParam("num") int num,
                               @RequestParam(value = "whatColumn", required = false) String whatColumn,
                               @RequestParam(value = "keyword",required = false) String keyword,
                               @RequestParam(value = "page",defaultValue = "1") int page,
                               Model model){

        model.addAttribute("whatColumn",whatColumn);
        model.addAttribute("keyword",keyword);
        model.addAttribute("page",page);

        FootballDto fDto = footballMapper.findByNum(num);
        model.addAttribute("fDto",fDto);
        return "content_view";
    }
    // 수정폼 띄우기
    @GetMapping("/modify")
    public String modifyForm(@RequestParam("num") int num,
                             @RequestParam(value = "whatColumn", required = false) String whatColumn,
                             @RequestParam(value = "keyword",required = false) String keyword,
                             @RequestParam(value = "page",defaultValue = "1") int page,
                             Model model) {

        model.addAttribute("whatColumn",whatColumn);
        model.addAttribute("keyword",keyword);
        model.addAttribute("page",page);

        FootballDto fDto = footballMapper.findByNum(num);
        model.addAttribute("fDto", fDto);
        model.addAttribute("nationList1", List.of("한국", "미국", "독일", "스페인"));
        model.addAttribute("nationList2", List.of("한국", "멕시코", "독일", "브라질", "스위스", "잉글랜드"));
        return "modify";
    }
    // 수정처리
    @PostMapping("/modifyProc")
    public String modify(@ModelAttribute("fDto") @Valid FootballDto fDto, BindingResult br,
                         @RequestParam(value = "whatColumn", required = false) String whatColumn,
                         @RequestParam(value = "keyword",required = false) String keyword,
                         @RequestParam(value = "page",defaultValue = "1") int page,
                         Model model){

        model.addAttribute("whatColumn",whatColumn);
        model.addAttribute("keyword",keyword);
        model.addAttribute("page",page);

        footballMapper.updateFootball(fDto);
        String encodeKeyword = keyword != null ? URLEncoder.encode(keyword, StandardCharsets.UTF_8) : "";
        return "redirect:/list?page="+page+"&whatColumn="+whatColumn+"&keyword="+encodeKeyword;
    }
    @GetMapping(value = "/delete")
    public String delete(@RequestParam("num") int num,
                         @RequestParam(value = "whatColumn", required = false) String whatColumn,
                         @RequestParam(value = "keyword",required = false) String keyword,
                         @RequestParam(value = "page",defaultValue = "1") int page,
                         Model model){

        model.addAttribute("whatColumn",whatColumn);
        model.addAttribute("keyword",keyword);
        model.addAttribute("page",page);

        footballMapper.deleteFootball(num);

        int limit = 3;
        int offset = (page - 1) * limit;
        Map<String, Object> params = new HashMap<>();
        params.put("whatColumn", whatColumn);
        params.put("keyword", keyword);
        params.put("offset", offset);
        params.put("limit", limit);

        int totalCount = footballMapper.getCount(params);
        if(totalCount % limit == 0 && page > 1) {
            page = page - 1;
        }
        String encodeKeyword = keyword != null ? URLEncoder.encode(keyword, StandardCharsets.UTF_8) : "";
        return "redirect:/list?page="+page+"&whatColumn="+whatColumn+"&keyword="+encodeKeyword;
    }

}
