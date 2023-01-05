package com.example.junit.service;

import com.example.junit.domain.BookRepository;
import com.example.junit.util.MailSenderStub;
import com.example.junit.web.dto.BookRespDto;
import com.example.junit.web.dto.BookSaveReqDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class BookServiceTest {
    @Autowired
    private BookRepository bookRepository;

    // 현재 상황: 서비스와 레포지토리 레이어가 함께 테스트 되는 상황
    @Test
    public void 책등록하기_테스트(){
        // given
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("junit");
        dto.setAuthor("metaCoding");

        // stub
        MailSenderStub mailSenderStub = new MailSenderStub();

        // when
         BookService bookService = new BookService(bookRepository, mailSenderStub);
         BookRespDto bookRespDto = bookService.insertBook(dto);

        // then
        assertEquals(dto.getTitle(), bookRespDto.getTitle());
        assertEquals(dto.getAuthor(), bookRespDto.getAuthor());
    }
}
