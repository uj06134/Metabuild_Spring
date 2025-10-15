package com.example.Ex02.service;

import com.example.Ex02.bean.MovieBean;
import com.example.Ex02.entity.MovieEntity;
import com.example.Ex02.repository.MovieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    @Autowired
    MovieRepository movieRepository;

    // ModelMapper: BookBean → BookEntity 간 필드 이름이 같을 때 자동 변환해주는 라이브러리
    private static ModelMapper modelMapper = new ModelMapper();

    public Page<MovieEntity> getMovieEntity(int page, int size, String keyword) {
        Pageable pageable= PageRequest.of(page,size, Sort.by("num").descending());
        if(keyword == null || keyword.isEmpty()){
            return movieRepository.findAll(pageable);
        } else {
            return movieRepository.searchByKeyword(keyword, pageable);
        }
    }

    public long count() {
        return movieRepository.count();
    }

    public MovieEntity beanToEntity(MovieBean movieBean) {
        return modelMapper.map(movieBean, MovieEntity.class);
    }

    public void saveMovie(MovieEntity movieEntity) {
        movieRepository.save(movieEntity);
    }

    public MovieEntity getByNum(int num) {
        return movieRepository.findById(num).orElse(null);
    }

    public void deleteMovie(int num) {
        movieRepository.deleteById(num);
    }
}
