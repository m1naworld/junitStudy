package com.example.junit.service;

import com.example.junit.domain.Book;
import com.example.junit.domain.BookRepository;
import com.example.junit.web.dto.BookRespDto;
import com.example.junit.web.dto.BookSaveReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    // 1. 책등록
    @Transactional(rollbackFor = RuntimeException.class)
    public BookRespDto insertBook(BookSaveReqDto dto){
        Book bookPS = bookRepository.save(dto.toEntity());
        return new BookRespDto().toDto(bookPS);
    }

    // 2. 책 목록 보기

    // 3. 책 한 권 보기

    // 4. 책 삭제

    // 5. 책 수정

}
