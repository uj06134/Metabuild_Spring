package com.example.Ex02.mapper;

import com.example.Ex02.dto.PersonDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PersonMapper {
    void insertPerson(PersonDto person);
    List<PersonDto> selectAll();
    PersonDto findByNum(int x);
    int updatePerson(PersonDto per);
    int deletePerson(int num);
}
