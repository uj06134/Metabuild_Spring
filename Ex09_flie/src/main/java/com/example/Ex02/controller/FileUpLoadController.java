package com.example.Ex02.controller;

import com.example.Ex02.dto.FileInfo;
import com.example.Ex02.service.FileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.core.io.Resource;

@Controller
public class FileUpLoadController {
    // Service 주입: 파일 저장 및 DB 처리 로직을 담당하는 FileService를 가져옴
    @Autowired
    FileService fileService;

    // 업로드 디렉토리 경로 (프로젝트 실행 경로 + /uploads/)
    public static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

    // form
    @GetMapping("/form")
    public String form(Model model){
        model.addAttribute("fileInfo", new FileInfo());
        return "form";
    }
    // 파일 업로드 처리
    @PostMapping("upload")
    public String upload(@Valid FileInfo fileInfo, BindingResult br,Model model){

        // file의 유효성 검사(FileInfo에서 불가능)
        if(fileInfo.getFile() == null || fileInfo.getFile().isEmpty()){
            br.rejectValue("file", "file.empty","파일을 선택하세요");
        }

        // 유효성 검증 실패 시: 다시 form.html 로 이동 + 에러 메시지 출력
        if(br.hasErrors()){
            model.addAttribute("fileInfo",fileInfo);
            model.addAttribute("message");

            return "form";
        }

        // 파일 업로드
        try {
            fileService.uploadFile(fileInfo);
            model.addAttribute("message", "업로드 성공");
        }catch (IOException e){
            model.addAttribute("message", "업로드 실패");
        }
        return "form";
    }
    // 업로드된 파일 목록 보기
    @GetMapping("/images")
    public String images(Model model){
        List<FileInfo> lists = fileService.getAllFiles();
        model.addAttribute("lists",lists);
        return "list";
    }

    // 실제 이미지 파일을 브라우저에 표시
    @GetMapping(value = "/images/{filename:.+}")
    public ResponseEntity<Resource> images(@PathVariable String filename) throws MalformedURLException {
        Path file = Paths.get(UPLOAD_DIR).resolve(filename).normalize();
        Resource resource = new UrlResource(file.toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .body(resource);
    }
}
