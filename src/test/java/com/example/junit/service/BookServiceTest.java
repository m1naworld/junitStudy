package com.example.junit.service;

import com.example.junit.domain.Book;
import com.example.junit.domain.BookRepository;
import com.example.junit.util.MailSender;
import com.example.junit.web.dto.BookRespDto;
import com.example.junit.web.dto.BookSaveReqDto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        assertThat(bookRespDto.getTitle()).isEqualTo(dto.getTitle());
        assertThat(bookRespDto.getAuthor()).isEqualTo(dto.getAuthor());
    }

    @Test
    public void 책목록보기_테스트(){
        // given
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "junit 강의", "metaCoding1"));
        books.add(new Book(2L, "spring 강의", "metaCoding2"));

        // stub
        when(bookRepository.findAll()).thenReturn(books);

        // when
        List<BookRespDto> bookRespDtoList = bookService.getBookList();
        bookRespDtoList.stream().forEach((b) -> {
            System.out.println("=========================== 테스트");
            System.out.println(b.getId());
            System.out.println(b.getTitle());
            System.out.println(b.getAuthor());
        });

        // then
        assertThat(bookRespDtoList.get(0).getTitle()).isEqualTo("junit 강의");
        assertThat(bookRespDtoList.get(0).getAuthor()).isEqualTo("metaCoding1");
        assertThat(bookRespDtoList.get(1).getTitle()).isEqualTo("spring 강의");
        assertThat(bookRespDtoList.get(1).getAuthor()).isEqualTo("metaCoding2");

    }

    @Test
    public void 책한권보기_테스트(){
        // given
        Long id = 1L;
        Book book = new Book(1L, "junit 강의", "metaCoding");
        Optional<Book> bookOP = Optional.of(book);

        // stub
        when(bookRepository.findById(id)).thenReturn(bookOP);

        // when
        BookRespDto bookRespDto = bookService.getBook(id);

        // then
        assertThat(bookRespDto.getTitle()).isEqualTo(book.getTitle());
        assertThat(bookRespDto.getAuthor()).isEqualTo(book.getAuthor());
    }
}
