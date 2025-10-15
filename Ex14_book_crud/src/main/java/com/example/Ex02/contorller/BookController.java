package com.example.Ex02.contorller;

import com.example.Ex02.bean.BookBean;
import com.example.Ex02.entity.BookEntity;
import com.example.Ex02.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

@Controller
public class BookController {

    @Autowired
    BookService bookService;

    // 도서 목록 조회
    @GetMapping(value = {"/", "blist"})
    public String getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(required = false) String keyword,
            Model model
    ){
        long totalCount = bookService.count();
        System.out.println("totalCount : " + totalCount);

        // Page Class
        // 현재페이지에 담길 레코드 bookList
        Page<BookEntity> bookList = bookService.getBookEntity(page,size,keyword); // 현재 페이지에 출력할 3가지
        System.out.println("bookList.getTotalElements() :" +bookList.getTotalElements()); //전체 리스트 가져오는 메서드
        System.out.println("bookList.getTotalPages() :" +bookList.getTotalPages());
        System.out.println("bookList.getNumber() :" + bookList.getNumber());
        System.out.println("bookList.getNumberOfElements()" + bookList.getNumberOfElements());

        model.addAttribute("bookList", bookList);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("keyword", keyword);
        model.addAttribute("totalCount", totalCount);

        return "book/select";
    }
    // 도서 등록 폼 이동
    @GetMapping(value = "book/insert")
    public String insertBook(Model model) {
        model.addAttribute("bookBean", new BookBean());
        return "book/insert";
    }

    // 도서 등록 처리
    @PostMapping("book/insert")
    public String insertBookProc(@Valid @ModelAttribute("bookBean") BookBean bookBean, BindingResult br) {
        if (br.hasErrors()) {
            return "book/insert";
        }
        // Bean → Entity 변환(DB 저장용)
        BookEntity bookEntity = bookService.beanToEntity(bookBean);
        bookService.saveBook(bookEntity);

        return "redirect:/blist";
    }

    // 상세조회
    @GetMapping(value = "/detail")
    public String detailBook(
            @RequestParam("no") int no,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "3") int size,
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {

        BookEntity bookEntity = bookService.getBookByNo(no);

        model.addAttribute("book", bookEntity);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("keyword", keyword);

        return "book/detail";
    }

    // 도서 수정 폼 이동
    @GetMapping(value = "/update")
    public String updateBook(@RequestParam("no") int no,
                             @RequestParam(value = "page", defaultValue = "0") int page,
                             @RequestParam(value = "size", defaultValue = "3") int size,
                             @RequestParam(value = "keyword", required = false) String keyword,
                             Model model) {
        BookEntity bookEntity = bookService.getBookByNo(no);
        // Entity → Bean 변환(수정 폼 초기값 세팅용)
        BookBean bookBean = bookService.entityToBean(bookEntity);

        model.addAttribute("bookBean", bookBean);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("keyword", keyword);
        return "book/update";
    }

    // 도서 수정 처리
    @PostMapping("book/update")
    public String updateBookProc(@Valid @ModelAttribute("bookBean") BookBean bookBean, BindingResult br,
                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "size", defaultValue = "3") int size,
                                 @RequestParam(value = "keyword", required = false) String keyword,
                                 Model model) {
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("keyword", keyword);

        if (br.hasErrors()) {
            return "book/update";
        }
        // Bean → Entity 변환(DB 저장용)
        BookEntity bookEntity = bookService.beanToEntity(bookBean);
        bookService.saveBook(bookEntity);

        String encodeKeyword = keyword != null ? URLEncoder.encode(keyword, StandardCharsets.UTF_8) : "";
        return "redirect:/blist?page="+page+"&size="+size+"&keyword="+encodeKeyword;
    }

    // 도서 삭제 처리
    @GetMapping(value = "/delete")
    public String deleteBook(@RequestParam("no") int no,
                             @RequestParam(value = "page", defaultValue = "0") int page,
                             @RequestParam(value = "size", defaultValue = "3") int size,
                             @RequestParam(value = "keyword", required = false) String keyword,
                             Model model) {
        bookService.deleteBook(no);

        Page<BookEntity> bookList = bookService.getBookEntity(page,size,keyword);
        if(bookList.getNumberOfElements()==0){
            page = page-1;
        }

        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("keyword", keyword);

        String encodeKeyword = keyword != null ? URLEncoder.encode(keyword, StandardCharsets.UTF_8) : "";
        return "redirect:/blist?page="+page+"&size="+size+"&keyword="+encodeKeyword;
    }

    // 도서(선택 항목) 삭제 처리
    @PostMapping("/book/checkDelete")
    public String checkDelete(@RequestParam("row") int[] row){
        for(int i=0;i<row.length;i++){
            bookService.deleteBook(row[i]);
        }

        return "redirect:/blist";
    }

    @ModelAttribute("kindArr")
    public List<String> kind(){
        List<String> kindList = new LinkedList<>();
        kindList.add("유료");
        kindList.add("무료");
        return kindList;
    }

    @ModelAttribute("bookstoreArr")
    public List<String> bookstore(){
        List<String> bookstoreList = new LinkedList<>();
        bookstoreList.add("교보문고");
        bookstoreList.add("알라딘");
        bookstoreList.add("yes24");
        bookstoreList.add("인터파크");
        return bookstoreList;
    }
}
