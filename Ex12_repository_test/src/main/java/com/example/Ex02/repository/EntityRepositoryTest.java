package com.example.Ex02.repository;

import com.example.Ex02.entity.EntityTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

// SpringBootTest import시 pom.xml에 있는 spring-boot-starter-test의 scope 삭제
@SpringBootTest
public class EntityRepositoryTest {

    @Autowired
    EntityRepository entityRepository;

    @Test
    @DisplayName("entity insert 테스트")
    public void entitySave(){
        EntityTest etest = new EntityTest();
        etest.setNum(10);
        etest.setName("kim");
        etest.setAddr("서울");
        etest.setAge(10);

        entityRepository.save(etest);
    }

    @Test
    @DisplayName("10개 레코드 삽입")
    public void e_testSave(){ // INSERT INTO e_test VALUES(~);
        String[] irum = {"park", "hong", "lee", "hwang"};
        String[] addr = {"서울", "부산", "제주"};
        int [] age = {10, 20, 30 ,40};

        for(int i=1;i<=10;i++){
            EntityTest etest = new EntityTest();
            etest.setNum(i);
            etest.setName(irum[i % irum.length]);
            etest.setAddr(addr[i % addr.length]);
            etest.setAge(age[i % age.length]);
            entityRepository.save(etest);
        }
    }

    @Test // 메서드 실행
    @DisplayName("전체 레코드 조회")
    public void findAllTest(){
        List<EntityTest> elists = entityRepository.findAll(); // SELECT * FROM e_test;
        for(EntityTest entity : elists){
            /*System.out.println(entity.getNum());
            System.out.println(entity.getName());
            System.out.println(entity.getAddr());
            System.out.println(entity.getAge());*/
            System.out.println(entity.toString());
            System.out.println("------------------");
        }
    }

    @Test // 메서드 실행
    @DisplayName("특정 레코드 조회")
    public void findName(){
        List<EntityTest> elists = entityRepository.findByName("park"); // SELECT * FROM e_test WHERE irum = 'park';
        for(EntityTest entity : elists){
            System.out.println(entity.toString());
            System.out.println("------------------");
        }
    }

    @Test // 메서드 실행
    @DisplayName("특정 레코드 조회")
    public void findAge(){
        List<EntityTest> elists = entityRepository.findByAgeGreaterThanEqual(30); // SELECT * FROM e_test WHERE age >= 30;
        for(EntityTest entity : elists){
            System.out.println(entity.toString());
            System.out.println("------------------");
        }
    }

}
