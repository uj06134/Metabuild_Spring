package com.example.Ex02.reposity;

import com.example.Ex02.entity.CompanyEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyRepository extends JpaRepository<CompanyEntity,Integer> {
    List<CompanyEntity> findBySalaryGreaterThanEqual(int i);

    List<CompanyEntity> findByNameContainingOrderByCompanyDesc(String 수);

    // nativeQuery=true → DB 스타일 (테이블 기준)
    @Query(value = "select * from company_entity", nativeQuery = true)
    // nativeQuery=false → JPA 스타일 (엔티티 기준)
    // @Query(value = "select c from CompanyEntity c", nativeQuery = false) // jpql
    List<CompanyEntity> findAllQuery();

    @Query(value = "select c from CompanyEntity c")
    List<CompanyEntity> findAllByJPQL();

    @Query("select count(c) from CompanyEntity c")
    int countCompany();

    @Query("select c from CompanyEntity c where c.name = :name")
    List<CompanyEntity> findByNameQuery(@Param("name") String name);

    @Query("select c from CompanyEntity c where c.name like %:name%")
    List<CompanyEntity> findByNameContainingQuery(@Param("name") String name);
}
