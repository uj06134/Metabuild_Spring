package com.example.Ex02.service;

import com.example.Ex02.bean.BookBean;
import com.example.Ex02.entity.BookEntity;
import com.example.Ex02.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

// Controller와 Repository의 중간 다리 역할
@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    // ModelMapper: BookBean → BookEntity 간 필드 이름이 같을 때 자동 변환해주는 라이브러리
    private static ModelMapper modelMapper = new ModelMapper();

    // 전체 레코드 수 조회
    public long count() {
        return bookRepository.count();
    }

    // 목록 조회 + 검색 + 페이징 처리
    public Page<BookEntity> getBookEntity(int page, int size, String keyword) {
        // page, size, Sort를 기반으로 페이징 객체(Pageable) 생성
        Pageable pageable= PageRequest.of(page,size, Sort.by("no").descending());
        if(keyword == null || keyword.isEmpty()){ // 검색어 X
            // X: 모든 도서 목록을 페이징하여 조회
            return bookRepository.findAll(pageable);
        } else {                                  // 검색어 O
            // O: 제목 또는 저자에 검색어가 포함된 데이터만 조회
            return bookRepository.findByTitleContainingOrAuthorContaining(keyword, keyword, pageable);
        }
    }

    // Bean <-> Entity 변환
// -------------------------------------------------------------
// 🔹 Bean(BookBean)은 화면(Form)에서 입력된 데이터를 담는 DTO 객체로,
//    @Valid 검증(유효성 검사)과 화면 바인딩(th:object)에 사용된다.
//
// 🔹 반면 Entity(BookEntity)는 실제 DB 테이블과 매핑된 객체로,
//    JPA가 insert/update/delete/query 등을 처리할 때 사용된다.
//
// 🔹 따라서 사용자가 입력한 데이터를 DB에 저장하려면,
//    화면용 BookBean → DB저장용 BookEntity 로 변환하는 과정이 필요하다.
//
// 🔹 이때 ModelMapper가 동일한 필드명을 자동 매핑하여
//    수동으로 set/get 하지 않아도 변환을 편리하게 해준다.

    // Bean → Entity 변환
    public BookEntity beanToEntity(BookBean bookBean) {
        return modelMapper.map(bookBean,BookEntity.class);
    }

    // insertProc/updateProc
    public void saveBook(BookEntity bookEntity) {
        bookRepository.save(bookEntity);
    }

    // detail/update
    public BookEntity getBookByNo(int no) {
        return bookRepository.findById(no).orElse(null); // 값이 있을 수도 있고 없을 수도 있는 Optional 객체를 반환
    }

    // Entity → Bean 변환
    public BookBean entityToBean(BookEntity bookEntity) {
        return modelMapper.map(bookEntity, BookBean.class);
    }

    public void deleteBook(int no) {
        bookRepository.deleteById(no);
    }
}
