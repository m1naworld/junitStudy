package com.example.junit.service;

import com.example.junit.domain.BookRepository;
import com.example.junit.util.MailSender;
import com.example.junit.web.dto.BookRespDto;
import com.example.junit.web.dto.BookSaveReqDto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private MailSender mailSender;

    @Test
    public void 책등록하기_테스트(){
        // given
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("junit");
        dto.setAuthor("metaCoding");

        // stub
        when(bookRepository.save(any())).thenReturn(dto.toEntity());
        when(mailSender.send()).thenReturn(true);

        // when
         BookRespDto bookRespDto = bookService.insertBook(dto);

        // then
//        assertEquals(dto.getTitle(), bookRespDto.getTitle());
//        assertEquals(dto.getAuthor(), bookRespDto.getAuthor());
        assertThat(dto.getTitle()).isEqualTo(bookRespDto.getTitle());
        assertThat(dto.getAuthor()).isEqualTo(bookRespDto.getAuthor());
    }
}
