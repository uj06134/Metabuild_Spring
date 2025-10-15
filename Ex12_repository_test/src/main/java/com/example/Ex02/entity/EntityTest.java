package com.example.Ex02.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor // 매개변수 없는 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 매개변수로 받는 전체 생성자 자동 생성
@Table(name = "eTest") // e_test 테이블명 지정(테이블명은 대문자 앞에 _ 생성
@Entity
public class EntityTest { // entity_test (테이블명은 대문자 앞에 _ 생성
    @Id // Primary Key(기본키) 필드를 지정
    private int num;
    @Column(name = "irum", nullable = false) // 실제 DB 컬럼명은 irum , NOT NULL 제약조건 적용
    private String name;
    private String addr;
    private int age;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "EntityTest{" +
                "num=" + num +
                ", name='" + name + '\'' +
                ", addr='" + addr + '\'' +
                ", age=" + age +
                '}';
    }
}
