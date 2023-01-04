package com.example.junit.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest // DB와 관련된 컴포넌만 메모리에 로딩
public class BookRepositoryTest {

    @Autowired // DI
    private BookRepository bookRepository;

    @BeforeEach // 각 테스트 시작 전 한번씩 실행  vs  @BeforeAll // 테스트 시작 전 한번만 실행
    public void 데이터준비(){
        String title = "junit";
        String author = "metaCoding";

        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();

        bookRepository.save(book);
    }
    // 트랜잭션 적용 범위
    // 가정 1: [데이터 준비() + 1 책등록] (T) + [데이터 준비() + 2 책목록보기] (T) -> 각 각의 트랜잭션에서 진행: 사이즈 1
    // 가정 2: [데이터 준비() + 1 책등록  + 데이터 준비() + 2 책 목록보기] (T ) -> 하나의 트랜잭션 안에서 진행 : 사이즈 2
    // 가정 1이 맞음!


    // 1. 책 등록
    @Test
    public void 책등록_test(){
        System.out.println("책등록_test 실행");
        // given(데이터 준비)
        String title = "junit 공부";
        String author = "mina";

        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();

        // when(테스트 실행)
        Book bookPS = bookRepository.save(book);

        // then(검증)
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());
    } // 트렌잭션 종료 (저장된 데이터 초기화)

    // 2. 책 목록보기
    @Test
    public void 책목록보기_test(){
        // given
        String title = "junit";
        String author = "metaCoding";

        // when
        List<Book> booksPS = bookRepository.findAll();
        System.out.println("======================= booksPS size: " + booksPS.size()); // 트랜잭션 범위 적용 확인

        // then
        assertEquals(title, booksPS.get(0).getTitle());
        assertEquals(author,booksPS.get(0).getAuthor());
    }

    // 3. 책 한 권 조회
    @Sql("classpath:db/tableInit.sql")
    @Test
    public void 책한권보기_test(){
        // given
        String title = "junit";
        String author = "metaCoding";

        // when
        Book bookPS = bookRepository.findById(1L).get();

        // then
        assertEquals(title, bookPS.getTitle());
        assertEquals(author,bookPS.getAuthor());
    }

    // 4. 책 삭제
    @Sql("classpath:db/tableInit.sql")
    @Test
    public void 책삭제_test(){
        // given
        Long id = 1L;

        // when
        bookRepository.deleteById(id);

        // then
        assertFalse(bookRepository.findById(id).isPresent());
    }

    // 5. 책 수정
    @Sql("classpath:db/tableInit.sql")
    @Test
    public void 책수정_test(){
        // given
        Long id = 1L;
        String title = "junit 공부";
        String author = "mina";

        Book book = new Book(id, title, author);

        // when
//        bookRepository.findAll().stream()
//                .forEach((b) -> {
//                    System.out.println("#1==========================");
//                    System.out.println(b.getId());
//                    System.out.println(b.getTitle());
//                    System.out.println(b.getAuthor());
//                });

        Book bookPS = bookRepository.save(book);

//        bookRepository.findAll().stream()
//                .forEach((b) -> {
//                    System.out.println("#2==========================");
//                    System.out.println(b.getId());
//                    System.out.println(b.getTitle());
//                    System.out.println(b.getAuthor());
//                });

        // then
        assertEquals(id, bookPS.getId());
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());
    }
}
