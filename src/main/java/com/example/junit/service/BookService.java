package com.example.junit.service;

import com.example.junit.domain.Book;
import com.example.junit.domain.BookRepository;
import com.example.junit.util.MailSender;
import com.example.junit.web.dto.BookRespDto;
import com.example.junit.web.dto.BookSaveReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final MailSender mailSender;

    // 1. 책등록
    @Transactional(rollbackFor = RuntimeException.class)
    public BookRespDto insertBook(BookSaveReqDto dto){
        Book bookPS = bookRepository.save(dto.toEntity());
        if (bookPS != null){
            // 메일보내기 메서드 호출 (return true or false)
            if(!mailSender.send()){
                throw new RuntimeException("메일이 전송되지 않았습니다.");
            }
        }
        return new BookRespDto().toDto(bookPS);
    }

    // 2. 책 목록 보기
    public List<BookRespDto> getBookList(){
        return bookRepository.findAll()
                .stream()
                .map(new BookRespDto() :: toDto)
                .collect(Collectors.toList());
    }

    // 3. 책 한 권 보기
    public BookRespDto getBook(Long id){
        Optional<Book> bookOP = bookRepository.findById(id);
        if(bookOP.isPresent()){
            return new BookRespDto().toDto(bookOP.get());
        }else{
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }
    }

    // 4. 책 삭제
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteBook(Long id){
        bookRepository.deleteById(id);
    }

    // 5. 책 수정
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateBook(Long id, BookSaveReqDto dto){
        Optional<Book> bookOP = bookRepository.findById(id);

        if (bookOP.isPresent()){
            Book bookPS = bookOP.get();
            bookPS.update(dto.getTitle(), dto.getAuthor());
        } else{
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }
    }
}
