package com.example.junit.web;

import com.example.junit.dto.response.BookResponseDto;
import com.example.junit.dto.request.BookSaveReqDto;
import com.example.junit.dto.response.CMResDto;
import com.example.junit.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BookApiController { // 컴포지션 = has 관계

    private final BookService bookService;

    // 1. 책등록
    @PostMapping("/api/v1/book")
    public ResponseEntity<?> saveBook(@RequestBody BookSaveReqDto bookSaveReqDto) {
        BookResponseDto bookResponseDto = bookService.책등록하기(bookSaveReqDto);
        CMResDto<?> cmResDto = CMResDto.builder()
                .code(1)
                .msg("글 저장 성공")
                .body(bookResponseDto)
                .build();
        return new ResponseEntity<>(cmResDto, HttpStatus.CREATED);
    }

    // 2. 책 목록 보기
    public ResponseEntity<?> getBookList() {
        return null;
    }

    // 3. 책 한권 보기
    public ResponseEntity<?> getBookOne() {
        return null;
    }

    // 4. 책 삭제 하기
    public ResponseEntity<?> deleteBook() {
        return null;
    }

    // 5. 책 수정 하기
    public ResponseEntity<?> updateBook() {
        return null;
    }
}
