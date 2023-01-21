package com.example.junit.service;

import com.example.junit.domain.BookRepository;
import com.example.junit.dto.BookResponseDto;
import com.example.junit.dto.BookSaveReqDto;
import com.example.junit.util.MailSenderStub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class BookServiceTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void 책등록하기_테스트() {
        // given
        BookSaveReqDto dto = new BookSaveReqDto("junit", "메타코딩");
        // stub
        MailSenderStub mailSenderStub = new MailSenderStub();

        // when
        BookService bookService = new BookService(bookRepository, mailSenderStub);
        BookResponseDto bookResponseDto = bookService.책등록하기(dto);

        // then
        Assertions.assertEquals(dto.getTitle(), bookResponseDto.getTitle());
        Assertions.assertEquals(dto.getAuthor(), bookResponseDto.getAuthor());
    }
}
