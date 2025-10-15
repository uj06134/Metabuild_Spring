package com.example.Ex02.reposity;

import com.example.Ex02.entity.CompanyEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CompnayRepositoryTest {

    @Autowired
    CompanyRepository companyRepository;

    @Test
    @DisplayName("1개 레코드 삽입")
    public void oneInsert(){
        CompanyEntity cEntity = new CompanyEntity();
        cEntity.setId(1);
        cEntity.setName("kim");
        cEntity.setCompany("카카오");
        cEntity.setPart("대리");
        cEntity.setSalary(300);

        companyRepository.save(cEntity);
    }

    @Test
    @DisplayName("10개 레코드 삽입")
    public void insert10(){
        String[] name = {"정국","수지","태형","원영"};
        String[] company = {"삼성","메타빌드","현대"};
        String[] part = {"대리","사원","과장"};
        int[] salary = {300,200,100,400};

        for(int i=1;i<=10;i++){
            CompanyEntity cEntity = new CompanyEntity();
            cEntity.setId(i);
            cEntity.setName(name[i % name.length]);
            cEntity.setCompany(company[i % company.length]);
            cEntity.setPart(part[i % part.length]);
            cEntity.setSalary(salary[i % salary.length]);
            companyRepository.save(cEntity);
        }
    }

    @Test
    @DisplayName("전체 레코드 조회")
    public void getAll() {
        List<CompanyEntity> companyList = companyRepository.findAll();
        for (CompanyEntity company : companyList) {
            System.out.println(company);
        }
    }

    @Test
    @DisplayName("급여가 300이상인 레코드 조회")
    public void getSalary(){
        List<CompanyEntity> companyList = companyRepository.findBySalaryGreaterThanEqual(300);
        for (CompanyEntity company : companyList){
            System.out.println(company);
        }
    }

    @Test
    @DisplayName("id가 5인 레코드 조회")
    public void getId(){
        Optional<CompanyEntity> company = companyRepository.findById(5);
            System.out.println(company);
    }

    @Test
    @DisplayName("이름에 '수'가 포함된 레코드(회사명 내림차순) 조회")
    public void getName(){
        List<CompanyEntity> companyList = companyRepository.findByNameContainingOrderByCompanyDesc("수");
        for (CompanyEntity company : companyList){
            System.out.println(company);
        }
    }

    // 쿼리 어노테이션 (Query annotation)
    @Test
    @DisplayName("Query annotation으로 전체 레코드 조회")
    public void selectAll(){
        List<CompanyEntity> companyList = companyRepository.findAllQuery();
        for (CompanyEntity company : companyList){
            System.out.println(company);
        }
    }

    @Test
    @DisplayName("Query annotation으로 전체 레코드 개수 조회")
    public void countCompany() {
        int count = companyRepository.countCompany();
            System.out.println("전체 레코드 개수: " + count);
    }

    @Test
    @DisplayName("Query annotation으로 이름이 '수지'인 레코드 조회")
    public void selectName(){
        List<CompanyEntity> companyList = companyRepository.findByNameQuery("수지");
        for (CompanyEntity company : companyList){
            System.out.println(company);
        }
    }

    @Test
    @DisplayName("Query annotation으로 이름이 '수'가 포함된 레코드 조회")
    public void selectNameContaining(){
        List<CompanyEntity> companyList = companyRepository.findByNameContainingQuery("수");
        for (CompanyEntity company : companyList){
            System.out.println(company);
        }
    }
}
