package com.example.junit.service;

import com.example.junit.domain.Book;
import com.example.junit.domain.BookRepository;
import com.example.junit.dto.BookResponseDto;
import com.example.junit.dto.BookSaveReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    // 1. 책 등록
    @Transactional(rollbackFor = RuntimeException.class)
    public BookResponseDto 책등록하기(BookSaveReqDto dto) {
        Book bookPS = bookRepository.save(dto.toEntity());
        return new BookResponseDto().toDto(bookPS);
    }

    // 2. 책 목록 보기
    public List<BookResponseDto> 책목록보기() {
        return bookRepository.findAll().stream()
                .map(new BookResponseDto()::toDto)
                .collect(Collectors.toList());
    }

    // 3. 책 한권 보기
    public BookResponseDto 책한권보기(Long id) {
        Book bookOP = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 아이디를 찾을 수 없습니다."));
        return new BookResponseDto().toDto(bookOP);
    }

    // 4. 책 삭제
    @Transactional(rollbackFor = RuntimeException.class)
    public void 책삭제하기(Long id) {
        bookRepository.deleteById(id);
        // 삭제할 id를 못찾았다고 DB에서 오류가 발생하진 않지만,
        // Spring에서 IllegalArgumentException를 발생시킴.
    }

    // 5. 책 수정
    @Transactional(rollbackFor = RuntimeException.class)
    public void 책수정하기(Long id, BookSaveReqDto dto) {
        Book bookPS = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 아이디를 찾을 수 없습니다."));

        bookPS.update(dto.getTitle(), dto.getAuthor());
        // 더티체킹으로 flush
    }
}
