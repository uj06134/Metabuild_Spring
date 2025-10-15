package com.example.Ex02.mapper;

import com.example.Ex02.dto.HotelDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface HotelMapper {
    List<HotelDto> selectAll(Map<String, Object> params);
    int getCount(Map<String, Object> params);
    void insertHotel(HotelDto hDto);
    HotelDto findByNum(int num);
    void updateHotel(HotelDto hDto);
    void deleteHotel(int num);
}
