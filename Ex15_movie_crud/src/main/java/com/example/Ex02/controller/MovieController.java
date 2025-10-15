package com.example.Ex02.controller;

import com.example.Ex02.bean.MovieBean;
import com.example.Ex02.entity.MovieEntity;
import com.example.Ex02.service.MovieService;
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
import java.util.List;

@Controller
public class MovieController {
    @Autowired
    MovieService movieService;

    @ModelAttribute("genreArr")
    public List<String> genreArr() {
        return List.of("공포", "코미디", "액션", "애니메이션");
    }

    @ModelAttribute("timeArr")
    public List<String> timeArr() {
        return List.of(
                "09:00~12:00",
                "12:00~15:00",
                "15:00~18:00",
                "18:00~21:00",
                "21:00~00:00",
                "00:00~03:00"
        );
    }

    @ModelAttribute("partnerArr")
    public List<Integer> partnerArr() {
        return List.of(1, 2, 3, 4, 5);
    }

    // 영화 목록 조회
    @GetMapping(value = {"/", "mlist"})
    public String getAllMovies(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "3") int size,
                               @RequestParam(required = false) String keyword,
                               Model model){

        long totalCount = movieService.count();

        Page<MovieEntity> movieList = movieService.getMovieEntity(page,size,keyword);

        model.addAttribute("movieList", movieList);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("keyword", keyword);
        model.addAttribute("totalCount", totalCount);
        return "movie/select";
    }

    // 영화 등록 폼 이동
    @GetMapping(value = "movie/insert")
    public String insertMovie(Model model) {
        model.addAttribute("movieBean", new MovieBean());
        return "movie/insert";
    }

    // 영화 등록 처리
    @PostMapping("movie/insert")
    public String insertMovieProc(@Valid @ModelAttribute("movieBean") MovieBean movieBean,
                                  BindingResult br) {
        if (br.hasErrors()) {
            return "movie/insert";
        }
        MovieEntity movieEntity = movieService.beanToEntity(movieBean);
        movieService.saveMovie(movieEntity);
        return "redirect:/mlist";
    }

    // 상세조회
    @GetMapping(value = "movie/detail")
    public String detailMovie(
            @RequestParam("num") int num,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "3") int size,
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {

        MovieEntity movieEntity = movieService.getByNum(num);

        model.addAttribute("movie", movieEntity);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("keyword", keyword);

        return "movie/detail";
    }

    // 영화 수정 폼 이동
    @GetMapping(value = "movie/update")
    public String updateMovie(@RequestParam("num") int num,
                             @RequestParam(value = "page", defaultValue = "0") int page,
                             @RequestParam(value = "size", defaultValue = "3") int size,
                             @RequestParam(value = "keyword", required = false) String keyword,
                             Model model) {
        MovieEntity movieEntity = movieService.getByNum(num);

        model.addAttribute("movieBean", movieEntity);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("keyword", keyword);
        return "movie/update";
    }

    // 영화 수정 처리
    @PostMapping(value = "movie/update")
    public String updateMovieProc(@Valid @ModelAttribute("movieBean") MovieBean movieBean, BindingResult br,
                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "size", defaultValue = "3") int size,
                                 @RequestParam(value = "keyword", required = false) String keyword,
                                 Model model) {

        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("keyword", keyword);

        if (br.hasErrors()) {
            return "movie/update";
        }
        MovieEntity movieEntity = movieService.beanToEntity(movieBean);
        movieService.saveMovie(movieEntity);

        String encodeKeyword = keyword != null ? URLEncoder.encode(keyword, StandardCharsets.UTF_8) : "";
        return "redirect:/mlist?page="+page+"&size="+size+"&keyword="+encodeKeyword;
    }

    // 영화 삭제 처리
    @GetMapping(value = "movie/delete")
    public String deleteMovie(@RequestParam("num") int num,
                              @RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "3") int size,
                              @RequestParam(value = "keyword", required = false) String keyword,
                              Model model) {
        movieService.deleteMovie(num);

        Page<MovieEntity> movieList = movieService.getMovieEntity(page,size,keyword);
        if(movieList.getNumberOfElements()==0){
            page = page - 1;
        }

        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("keyword", keyword);

        String encodeKeyword = keyword != null ? URLEncoder.encode(keyword, StandardCharsets.UTF_8) : "";
        return "redirect:/mlist?page="+page+"&size="+size+"&keyword="+encodeKeyword;
    }

    @PostMapping(value = "movie/checkDelete")
    public String deleteCheck(@RequestParam("row") int[] row){
        for(int i=0;i<row.length;i++){
            movieService.deleteMovie(row[i]);
        }
        return "redirect:/mlist";
    }
}
