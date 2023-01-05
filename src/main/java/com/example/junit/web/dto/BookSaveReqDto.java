package com.example.junit.web.dto;

import com.example.junit.domain.Book;
import lombok.Getter;

@Getter
public class BookSaveReqDto {

    private String title;
    private String author;

    public Book toEntity(){
        return Book.builder()
                .title(title)
                .author(author)
                .build();
    }
}
