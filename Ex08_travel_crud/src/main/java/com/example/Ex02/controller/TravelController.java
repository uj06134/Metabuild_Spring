package com.example.Ex02.controller;

import com.example.Ex02.dto.TravelDto;
import com.example.Ex02.mapper.TravelMapper;
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
public class TravelController {
    @Autowired
    private TravelMapper travelMapper;

    @GetMapping(value = "/insert")
    public String insert(@ModelAttribute("tDto") TravelDto tDto) {
        return "insert";
    }

    @PostMapping(value = "/insertProc")
    public String insertProc(@ModelAttribute("tDto") @Valid TravelDto tDto, BindingResult br) {
        String page;
        if (br.hasErrors()) {
            page = "insert";
        } else {
                travelMapper.insertTravel(tDto);
            page = "redirect:/list";
        }
        return page;
    }
    // 여행 목록 조회
    // - 검색 조건(whatColumn, keywordm, page) 파라미터 받을 수 있음
    // - insert/update/delete 작업 후에도 list 페이지로 redirect
    @GetMapping(value = "/list")
    public String list(Model model,
                       // 검색할 컬럼명 (name, area, style, all 등)
                       @RequestParam(value = "whatColumn",required = false) String whatColumn,
                       // 검색어 (예: "동남아", "골프")
                       @RequestParam(value = "keyword",required = false) String keyword,
                       // 현재 페이지 번호 (기본값 1)
                       @RequestParam(value = "page",defaultValue = "1") int page) {
        int limit = 5; // 한 페이지에 보여줄 데이터 개수
        int offset = (page - 1) * limit; // 건너뛸 데이터 개수 (시작 위치)

        // Mapper에 넘길 파라미터를 key-value로 담는 Map 생성
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("whatColumn", whatColumn);
        params.put("keyword", keyword);
        params.put("offset", offset);
        params.put("limit", limit);

        // 실제 데이터 목록 조회 (검색 + 페이징 반영됨)
        List<TravelDto> lists = travelMapper.selectAll(params);

        // 전체 데이터 개수 가져오기 (검색 조건이 반영됨)
        int totalCount = travelMapper.getCount(params);

        // 전체 페이지 수 계산
        // 예: totalCount=10, limit=3 → 10/3 = 3.333 → 올림해서 4 페이지
        int totalPage = (int)Math.ceil((double)totalCount/limit);

        // View로  전달할 데이터 설정
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
                               @RequestParam(value = "whatColumn",required = false) String whatColumn,
                               @RequestParam(value = "keyword",required = false) String keyword,
                               @RequestParam(value = "page",defaultValue = "1") int page,
                               Model model){

        // 다시 list로 redirect할 때 검색/페이징 정보 전달을 위해 Model에 담음
        model.addAttribute("whatColumn",whatColumn);
        model.addAttribute("keyword",keyword);
        model.addAttribute("page",page);

        TravelDto tDto = travelMapper.findByNum(num);
        model.addAttribute("tDto",tDto);
        return "content_view";
    }
    // 수정폼 띄우기
    @GetMapping("/update")
    public String update(@RequestParam("num") int num, Model model,
                         @RequestParam(value = "whatColumn",required = false) String whatColumn,
                         @RequestParam(value = "keyword",required = false) String keyword,
                         @RequestParam(value = "page",defaultValue = "1") int page) {

        // 다시 list로 redirect할 때 검색/페이징 정보 전달을 위해 Model에 담음
        model.addAttribute("whatColumn",whatColumn);
        model.addAttribute("keyword",keyword);
        model.addAttribute("page",page);

        TravelDto tDto = travelMapper.findByNum(num);
        model.addAttribute("tDto", tDto);
        model.addAttribute("areaArr", List.of("유럽", "동남아", "일본", "중국"));
        model.addAttribute("styleArr", List.of("패키지", "크루즈", "자유여행", "골프여행"));
        model.addAttribute("priceArr", List.of("100~200", "200~300", "300~400"));
        return "update";
    }

    // 수정 처리 (유효성 검증 포함)
    @PostMapping("/updateProc")
    public String updateProc(@ModelAttribute("tDto") @Valid TravelDto tDto, BindingResult br, Model model,
                             @RequestParam(value = "whatColumn",required = false) String whatColumn,
                             @RequestParam(value = "keyword",required = false) String keyword,
                             @RequestParam(value = "page",defaultValue = "1") int page) {

        // 다시 list로 redirect할 때 검색/페이징 정보 전달을 위해 Model에 담음
        model.addAttribute("whatColumn",whatColumn);
        model.addAttribute("keyword",keyword);
        model.addAttribute("page",page);

        String resultPage;
        model.addAttribute("areaArr", List.of("유럽", "동남아", "일본", "중국"));
        model.addAttribute("styleArr", List.of("패키지", "크루즈", "자유여행", "골프여행"));
        model.addAttribute("priceArr", List.of("100~200", "200~300", "300~400"));
        if (br.hasErrors()) {
            resultPage = "update";
        } else {
            travelMapper.updateTravel(tDto);
            // keyword에 한글이 들어가면 깨질 수 있으므로 UTF-8로 인코딩 처리
            String encodeKeyword = keyword != null ? URLEncoder.encode(keyword, StandardCharsets.UTF_8) : "";
            resultPage = "redirect:/list?page="+page+"&whatColumn="+whatColumn+"&keyword="+encodeKeyword;
        }
        return resultPage;
    }
    @GetMapping(value = "/delete")
    public String delete(@RequestParam("num") int num, Model model,
                         @RequestParam(value = "whatColumn",required = false) String whatColumn,
                         @RequestParam(value = "keyword",required = false) String keyword,
                         @RequestParam(value = "page",defaultValue = "1") int page) {


        int limit = 5;
        int offset = (page - 1) * limit;
        Map<String, Object> params = new HashMap<>();
        params.put("whatColumn", whatColumn);
        params.put("keyword", keyword);
        params.put("offset", offset);
        params.put("limit", limit);

        // 만약 totalCount가 limit의 배수라면 (= 마지막 페이지의 데이터가 딱 맞아떨어짐)
        // 삭제하면 현재 페이지가 비게 되므로, 이전 페이지로 이동해야 함
        int totalCount = travelMapper.getCount(params);
        if(totalCount % limit == 0 && page > 1) {
            page = page - 1;
        }

        // 다시 list로 redirect할 때 검색/페이징 정보 전달을 위해 Model에 담음
        model.addAttribute("whatColumn",whatColumn);
        model.addAttribute("keyword",keyword);
        model.addAttribute("page",page);

        travelMapper.deleteTravel(num);

        // keyword에 한글이 들어가면 깨질 수 있으므로 UTF-8로 인코딩 처리
        String encodeKeyword = keyword != null ? URLEncoder.encode(keyword, StandardCharsets.UTF_8) : "";
        return "redirect:/list?page="+page+"&whatColumn="+whatColumn+"&keyword="+encodeKeyword;
    }



}
