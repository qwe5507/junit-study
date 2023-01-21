package com.example.junit.web;

import com.example.junit.web.dto.response.BookResponseDto;
import com.example.junit.web.dto.request.BookSaveReqDto;
import com.example.junit.web.dto.response.CMResDto;
import com.example.junit.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BookApiController { // 컴포지션 = has 관계

    private final BookService bookService;

    // 1. 책등록
    @PostMapping("/api/v1/book")
    public ResponseEntity<?> saveBook(@RequestBody @Valid BookSaveReqDto bookSaveReqDto, BindingResult bindingResult) {
        BookResponseDto bookResponseDto = bookService.책등록하기(bookSaveReqDto);

        // TODO AOP 처리하는 게 좋음
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }
            return new ResponseEntity<>(CMResDto.builder()
                    .code(-1)
                    .msg(errorMap.toString())
                    .body(bookResponseDto)
                    .build(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(CMResDto.builder()
                .code(1)
                .msg("글 저장 성공")
                .body(bookResponseDto)
                .build(), HttpStatus.CREATED);
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
