package com.example.junit.web;

import com.example.junit.service.BookService;
import com.example.junit.web.dto.request.BookSaveReqDto;
import com.example.junit.web.dto.response.BookListResDto;
import com.example.junit.web.dto.response.BookResDto;
import com.example.junit.web.dto.response.CMResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class BookApiController { // 컴포지션 = has 관계

    private final BookService bookService;

    // 1. 책등록
    @PostMapping("/book")
    public ResponseEntity<?> saveBook(@RequestBody @Valid BookSaveReqDto bookSaveReqDto, BindingResult bindingResult) {

        // TODO AOP 처리하는 게 좋음
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }
            throw new RuntimeException(errorMap.toString());
        }

        BookResDto bookResponseDto = bookService.책등록하기(bookSaveReqDto);
        return new ResponseEntity<>(CMResDto.builder()
                .code(1)
                .msg("글 저장 성공")
                .body(bookResponseDto)
                .build(), HttpStatus.CREATED);
    }

    // 2. 책 목록 보기
    @GetMapping("/book")
    public ResponseEntity<?> getBookList() {
        BookListResDto bookListResDto = bookService.책목록보기();
        return new ResponseEntity<>(CMResDto.builder()
                .code(1)
                .msg("글 목록보기 성공")
                .body(bookListResDto) // body에 json, list와 같이 다른 형식으로 내리는건 좋지 않기때문에 json으로 감싸서 응답
                .build(), HttpStatus.OK);
    }

    // 3. 책 한권 보기
    @GetMapping("/book/{id}")
    public ResponseEntity<?> getBookOne(@PathVariable Long id) {
        BookResDto bookResDto = bookService.책한권보기(id);
        return new ResponseEntity<>(CMResDto.builder()
                .code(1)
                .msg("글 한건보기 성공")
                .body(bookResDto)
                .build(), HttpStatus.OK);
    }

    // 4. 책 삭제 하기
    @DeleteMapping("/book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookService.책삭제하기(id);
        return new ResponseEntity<>(CMResDto.builder()
                .code(1)
                .msg("글 삭제하기 성공")
                .body(null)
                .build(), HttpStatus.OK);
    }

    // 5. 책 수정 하기
    @PutMapping("/book/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody @Valid BookSaveReqDto bookSaveReqDto, BindingResult bindingResult) {

        // TODO AOP 처리하는 게 좋음
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }
            throw new RuntimeException(errorMap.toString());
        }
        BookResDto bookResDto = bookService.책수정하기(id, bookSaveReqDto);
        return new ResponseEntity<>(CMResDto.builder()
                .code(1)
                .msg("글 수정하기 성공")
                .body(bookResDto)
                .build(), HttpStatus.OK);
    }
}
