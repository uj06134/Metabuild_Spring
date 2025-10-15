package com.example.Ex02.service;

import com.example.Ex02.dto.FileInfo;
import com.example.Ex02.mapper.FileMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileService {
    // MyBatis Mapper 주입: DB와의 연동 담당
    @Autowired
    private FileMapper fileMapper;

    // 업로드 디렉토리 경로 설정
    @Value("${upload.dir:${user.dir}/uploads}")
    private String uploadDir; // c:Ex09/uploads

    // 서비스 초기화 메서드
    @PostConstruct
    public void init() throws IOException {
        System.out.println("init");

        Files.createDirectories(Paths.get(uploadDir));
    }
    // 파일 업로드 처리
    public void uploadFile(FileInfo fileInfo) throws IOException {
        String name = fileInfo.getName();           // 사용자가 입력한 이름
        MultipartFile file = fileInfo.getFile();    // 업로드된 실제 파일

        String original = file.getOriginalFilename();
        System.out.println("name:" + name);
        System.out.println("original:" + original);

        // 저장할 경로 생성 (업로드 폴더 + 파일명)
        Path target = Paths.get(uploadDir, original);

        // // MultipartFile → 실제 서버 파일로 저장
        file.transferTo(target.toFile());

        // DTO에 파일 정보 세팅
        fileInfo.setFilename(original);
        fileInfo.setFileSize((int)file.getSize());

        // DB에 파일 정보 저장 (이름, 파일명, 크기 등)
        fileMapper.saveFileInfo(fileInfo);
    }

    // 업로드된 모든 파일 목록 조회
    public List<FileInfo> getAllFiles() {
        return fileMapper.getAllFiles();
    }
}
