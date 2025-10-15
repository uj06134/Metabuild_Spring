package com.example.Ex02.controller;

import com.example.Ex02.dto.ProductsDto;
import com.example.Ex02.mapper.ProductsMapper;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProductsController {
    @Value("${upload.dir:${user.dir}/uploads}")
    private String uploadDir;

    // uploadDir 경로가 존재하지 않으면 자동으로 디렉토리를 생성
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadDir));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    private ProductsMapper productsMapper;


    @GetMapping("/plist.prd")
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
        List<ProductsDto> lists = productsMapper.selectAll(params);
        int totalCount = productsMapper.getCount(params);
        int totalPage = (int) Math.ceil((double) totalCount / limit);

        // 4. 조회 결과 + 페이징/검색 정보 모델에 담기
        model.addAttribute("productsLists", lists);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("whatColumn", whatColumn);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);

        // 5. 뷰 리턴
        return "products/productsList";
    }

    @GetMapping(value = "/pinsert.prd")
    public String insertForm(@ModelAttribute("pDto") ProductsDto pDto, HttpSession session) {
        if (session.getAttribute("loginInfo") == null) { // 로그인 성공 실패
            session.setAttribute("destination", "redirect:/pinsert.prd");
            return "redirect:/login.mb";
        } else {

            return "products/productInsert";
        }
    }

    @PostMapping("pinsert.prd")
    public String insert(@Valid @ModelAttribute("pDto") ProductsDto pDto,
                         BindingResult br,
                         Model model) {
        // 유효성 검증
        if (pDto.getFile() == null || pDto.getFile().isEmpty()) {
            br.rejectValue("file", "file.empty", "파일을 선택해주세요.");
        }
        if (br.hasErrors()) {
            return "products/productInsert";
        }

        // 폴더 업로드
        MultipartFile file = pDto.getFile();
        String original = file.getOriginalFilename(); // 파일명
        Path target = Paths.get(uploadDir, original);
        try {
            file.transferTo(target.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        pDto.setImage(original); // dto 파일명 삽입
        productsMapper.insertProduct(pDto); // DB insert
        return "redirect:/plist.prd";
    }

    @GetMapping(value = "/pview.prd")
    public String content_view(@RequestParam("num") int num,
                               @RequestParam(value = "whatColumn", required = false) String whatColumn,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "page", defaultValue = "1") int page,
                               Model model) {

        model.addAttribute("whatColumn", whatColumn);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);

        ProductsDto pDto = productsMapper.findByNum(num);
        model.addAttribute("pDto", pDto);
        return "products/productContent_view";
    }

    @GetMapping(value = "/images/{images}")
    @ResponseBody
    public ResponseEntity images(@PathVariable String images) throws MalformedURLException {
        Path file = Paths.get(uploadDir).resolve(images).normalize();
        Resource resource = new UrlResource(file.toUri());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + images + "\"")
                .body(resource);
    }

    @GetMapping("/pupdate.prd")
    public String updateForm(@RequestParam("num") int num, Model model, HttpSession session,
                             @RequestParam(value = "whatColumn", required = false) String whatColumn,
                             @RequestParam(value = "keyword", required = false) String keyword,
                             @RequestParam(value = "page", defaultValue = "1") int page) {

        if (session.getAttribute("loginInfo") == null) { // 로그인 성공 실패
            String encodeKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
            session.setAttribute("destination", "redirect:/pupdate.prd?num=" + num
                    + "&whatColumn=" + whatColumn
                    + "&keyword=" + encodeKeyword
                    + "&page=" + page);
            return "redirect:/login.mb";
        } else {
            model.addAttribute("whatColumn", whatColumn);
            model.addAttribute("keyword", keyword);
            model.addAttribute("page", page);

            ProductsDto pDto = productsMapper.findByNum(num);
            model.addAttribute("pDto", pDto);

            return "products/productUpdate";
        }
    }

    @PostMapping("/pupdate.prd")
    public String updateProc(@ModelAttribute("pDto") @Valid ProductsDto pDto, BindingResult br, Model model, HttpSession session,
                             @RequestParam(value = "whatColumn", required = false) String whatColumn,
                             @RequestParam(value = "keyword", required = false) String keyword,
                             @RequestParam(value = "page", defaultValue = "1") int page) {

        if (session.getAttribute("loginInfo") == null) { // 로그인 성공 실패
            String encodeKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
            session.setAttribute("destination", "redirect:/pupdate.prd?num=" + pDto.getNum()
                    + "&whatColumn=" + whatColumn
                    + "&keyword=" + encodeKeyword
                    + "&page=" + page);
            return "redirect:/login.mb";
        } else {
            model.addAttribute("whatColumn", whatColumn);
            model.addAttribute("keyword", keyword);
            model.addAttribute("page", page);

            // 유효성 검증
            if (pDto.getFile() == null || pDto.getFile().isEmpty()) {
                br.rejectValue("file", "file.empty", "파일을 선택해주세요.");
            }
            if (br.hasErrors()) {
                return "products/productUpdate";
            }

            // 폴더 업로드
            MultipartFile file = pDto.getFile();
            String original = file.getOriginalFilename(); // 파일명
            Path target = Paths.get(uploadDir, original);
            try {
                file.transferTo(target.toFile());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            pDto.setImage(original); // dto 파일명 삽입
            productsMapper.updateProduct(pDto); // DB update

            String encodeKeyword = keyword != null ? URLEncoder.encode(keyword, StandardCharsets.UTF_8) : "";
            return "redirect:/plist.prd?page=" + page + "&whatColumn=" + whatColumn + "&keyword=" + encodeKeyword;
        }
    }

    @GetMapping("/pdelete.prd")
    public String deleteProc(@RequestParam("num") int num, Model model, HttpSession session,
                             @RequestParam(value = "whatColumn", required = false) String whatColumn,
                             @RequestParam(value = "keyword", required = false) String keyword,
                             @RequestParam(value = "page", defaultValue = "1") int page) {

        if (session.getAttribute("loginInfo") == null) { // 로그인 성공 실패
            String encodeKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
            // destination 저장할 때
            session.setAttribute("destination", "redirect:/plist.prd?page=" + page
                    + "&whatColumn="
                    + whatColumn
                    + "&keyword="
                    + keyword);

            return "redirect:/login.mb";
        } else {
            // 상품 정보 가져오기
            ProductsDto pDto = productsMapper.findByNum(num);
            Path filePath = Paths.get(uploadDir, pDto.getImage()); // 경로 정보
            File file = filePath.toFile(); // Path → File 객체 변환
            file.delete();

            productsMapper.deleteProduct(num);

            int limit = 3;
            int offset = (page - 1) * limit;

            Map<String, Object> params = new HashMap<>();
            params.put("whatColumn", whatColumn);
            params.put("keyword", keyword);
            params.put("offset", offset);
            params.put("limit", limit);

            int totalCount = productsMapper.getCount(params);

            if (totalCount % limit == 0) {
                page = page - 1;
            }

            model.addAttribute("whatColumn", whatColumn);
            model.addAttribute("keyword", keyword);
            model.addAttribute("page", page);

            String encodeKeyword = keyword != null ? URLEncoder.encode(keyword, StandardCharsets.UTF_8) : "";
            return "redirect:/plist.prd?page=" + page + "&whatColumn=" + whatColumn + "&keyword=" + encodeKeyword;
        }
    }
}
