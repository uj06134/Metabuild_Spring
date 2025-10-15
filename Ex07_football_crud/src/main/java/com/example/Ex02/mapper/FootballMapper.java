package com.example.Ex02.mapper;

import com.example.Ex02.dto.FootballDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FootballMapper {
    void insertFootball(FootballDto fDto);
    List<FootballDto> selectAll(Map<String,Object> map);
    FootballDto findByNum(int num);
    void updateFootball(FootballDto fDto);
    void deleteFootball(int num);
    int getCount(Map<String,Object> map);
}
