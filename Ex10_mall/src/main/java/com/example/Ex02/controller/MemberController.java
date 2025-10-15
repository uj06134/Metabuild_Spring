package com.example.Ex02.controller;

import com.example.Ex02.dto.MemberDto;
import com.example.Ex02.mapper.MemberMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MemberController {
    @Autowired
    private MemberMapper memberMapper;

    @GetMapping("/mlist.mb")
    public String list(Model model,
                       @RequestParam(value = "whatColumn", required = false) String whatColumn,
                       @RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "page", defaultValue = "1") int page) {

        // 1. 페이징 처리 기본값
        int limit = 3;
        int offset = (page - 1) * limit;

        // 2. 검색 및 페이징 파라미터 준비
        Map<String, Object> params = new HashMap<>();
        params.put("whatColumn", whatColumn);
        params.put("keyword", keyword);
        params.put("offset", offset);
        params.put("limit", limit);

        // 3. 데이터 조회
        List<MemberDto> lists = memberMapper.selectAll(params);
        int totalCount = memberMapper.getCount(params);
        int totalPage = (int) Math.ceil((double) totalCount / limit);

        // 4. 조회 결과 + 페이징/검색 정보 모델에 담기
        model.addAttribute("memberLists", lists);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("whatColumn", whatColumn);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);

        // 5. 뷰 리턴
        return "member/memberList";
    }


    @GetMapping(value = "/minsert.mb")
    public String insertForm(@ModelAttribute("mDto") MemberDto mDto) {
        return "member/memberInsert";
    }

    @PostMapping(value = "/minsert.mb")
    public String insertProc(@ModelAttribute("mDto") @Valid MemberDto mDto, BindingResult br) {
        if (br.hasErrors()) {
            return "member/memberInsert";
        }
        memberMapper.insertMember(mDto);
        return "redirect:/mlist.mb";
    }

    @GetMapping("/mupdate.mb")
    public String updateForm(@RequestParam("id") String id, Model model,
                         @RequestParam(value = "whatColumn",required = false) String whatColumn,
                         @RequestParam(value = "keyword",required = false) String keyword,
                         @RequestParam(value = "page",defaultValue = "1") int page) {

        // 1. 수정할 회원 가져오기
        MemberDto mDto = memberMapper.findById(id);
        model.addAttribute("mDto", mDto);

        // 2. 검색/페이징 조건 유지
        model.addAttribute("whatColumn",whatColumn);
        model.addAttribute("keyword",keyword);
        model.addAttribute("page",page);

        // 3. 수정 화면 이동
        return "member/memberUpdate";
    }

    @PostMapping("/mupdate.mb")
    public String updateProc(@ModelAttribute("mDto") @Valid MemberDto mDto, BindingResult br, Model model,
                             @RequestParam(value = "whatColumn", required = false) String whatColumn,
                             @RequestParam(value = "keyword", required = false) String keyword,
                             @RequestParam(value = "page", defaultValue = "1") int page) {

        // 1. 검색/페이징 조건 유지
        model.addAttribute("whatColumn", whatColumn);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);

        // 2. 유효성 검사 (에러 있으면 다시 수정폼으로)
        if (br.hasErrors()) {
            return "member/memberUpdate";
        }

        // 3. DB 업데이트 처리
        memberMapper.updateMember(mDto);

        // 4. 검색 키워드 인코딩
        String encodeKeyword = keyword != null ? URLEncoder.encode(keyword, StandardCharsets.UTF_8) : "";

        // 5. 목록 화면으로 리다이렉트 (검색/페이지 조건 유지)
        return "redirect:/mlist.mb?page="+page+"&whatColumn="+whatColumn+"&keyword="+encodeKeyword;
    }
    @GetMapping("/mdelete.mb")
    public String deleteProc(@RequestParam("id") String id, Model model,
                         @RequestParam(value = "whatColumn",required = false) String whatColumn,
                         @RequestParam(value = "keyword",required = false) String keyword,
                         @RequestParam(value = "page",defaultValue = "1") int page) {

        // 1. 삭제 (삭제 후에 count조회 해야 정확)
        memberMapper.deleteMember(id);

        // 2. 페이징을 위한 기본값 설정
        int limit = 3;
        int offset = (page - 1) * limit;

        // 3. 검색 조건 + 페이징 정보를 Map에 담음
        Map<String, Object> params = new HashMap<>();
        params.put("whatColumn", whatColumn);
        params.put("keyword", keyword);
        params.put("offset", offset);
        params.put("limit", limit);

        // 4. 삭제 후 전체 레코드 수 조회
        int totalCount = memberMapper.getCount(params);

        // 5. 마지막 페이지가 비어 있으면 이전 페이지로 이동
        if(totalCount % limit == 0 && page > 1) {
            page = page - 1;
        }

        // 6. 모델에 검색 조건 및 페이지 정보 저장 (뷰에 넘기기용)
        model.addAttribute("whatColumn",whatColumn);
        model.addAttribute("keyword",keyword);
        model.addAttribute("page",page);

        // 7. 검색 키워드 인코딩
        String encodeKeyword = keyword != null ? URLEncoder.encode(keyword, StandardCharsets.UTF_8) : "";

        // 8. 목록 화면으로 리다이렉트 (검색/페이지 조건 유지)
        return "redirect:/mlist.mb?page="+page+"&whatColumn="+whatColumn+"&keyword="+encodeKeyword;
    }
    // 아이디 중복 검사 요청 처리
    @RequestMapping(value="/checkId.mb")
    @ResponseBody // View 이름을 리턴하는 게 아니라, return 값을 "그대로 HTTP 응답 본문"에 담아서 보냄
    public String checkDuplicate(@RequestParam("id") String id){
        System.out.println("checkId.mb");
        int count = memberMapper.selectCountById(id);
        System.out.println("count:" + count);
        String result = "";
        if(count > 0){
            result = "duplicate";
        } else{
            result = "available";
        }
        return result;
    }

    @GetMapping(value="/login.mb")
    public String login(){
        return "member/memberLoginForm";
    }

    @PostMapping(value="/login.mb")
    public String loginProc(MemberDto mDto, HttpServletResponse response, HttpSession session) throws IOException {
        MemberDto member = memberMapper.findById(mDto.getId());
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter pw;
        pw = response.getWriter(); // 브라우저와 연결다리 형성

        if(member == null){
            pw.println("<script type='text/javascript'>");
            pw.println("alert('해당 아이디가 존재하지 않습니다.')");
            pw.println("</script>");
            pw.flush();
            return "member/memberLoginForm";

        } else{
            if(member.getPassword().equals(mDto.getPassword())){
                session.setAttribute("loginInfo",member); // 세션 설정
                String destination = (String)session.getAttribute("destination");
                if(session.getAttribute("destination") == null){
                    return "home";
                } else{
                    return destination;
                }
            } else{
                pw.println("<script type='text/javascript'>");
                pw.println("alert('비밀번호가 일치하지 않습니다.')");
                pw.println("</script>");
                pw.flush();
                return "member/memberLoginForm";
            }
        }
    }
    @GetMapping(value="/logout.mb")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
}
