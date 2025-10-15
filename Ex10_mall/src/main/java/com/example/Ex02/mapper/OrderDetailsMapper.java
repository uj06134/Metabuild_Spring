package com.example.Ex02.mapper;

import com.example.Ex02.dto.OrderDetailDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDetailsMapper {
    void insertOrderDetails(OrderDetailDto odDto);
}
