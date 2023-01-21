package com.example.junit.service;

import com.example.junit.domain.Book;
import com.example.junit.domain.BookRepository;
import com.example.junit.dto.BookResponseDto;
import com.example.junit.dto.BookSaveReqDto;
import com.example.junit.util.MailSender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) //가짜 환경 생성
public class BookServiceTest {

    @InjectMocks //Mock객체가 주입된 객체 생성
    private BookService bookService;
    @Mock // Mock객체
    private BookRepository bookRepository;
    @Mock // Mock객체
    private MailSender mailSender;

    @Test
    public void 책등록하기_테스트() {
        // given
        BookSaveReqDto dto = new BookSaveReqDto("junit", "메타코딩");

        // stub
        // mocking할떄의 주소 값과, 실제 메서드가 실행될떄의 주소가 다르면 에러발생, 현재는 주소 값을 비교할 필요가 없어 any()
        when(bookRepository.save(any())).thenReturn(dto.toEntity());
        when(mailSender.send()).thenReturn(true);

        // when
        BookResponseDto bookResponseDto = bookService.책등록하기(dto);

        // then
        assertThat(bookResponseDto.getTitle()).isEqualTo(dto.getTitle());
        assertThat(bookResponseDto.getAuthor()).isEqualTo(dto.getAuthor());
    }

    @Test
    public void 책목록보기_테스트() {
        // given
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "junit강의", "겟인데어"));
        books.add(new Book(2L, "DDD", "이진강"));

        // stub
        when(bookRepository.findAll()).thenReturn(books);

        // when
        List<BookResponseDto> bookResponseDtoList = bookService.책목록보기();

        // then
        assertThat(bookResponseDtoList.get(0).getTitle()).isEqualTo("junit강의");
        assertThat(bookResponseDtoList.get(0).getAuthor()).isEqualTo("겟인데어");
        assertThat(bookResponseDtoList.get(1).getTitle()).isEqualTo("DDD");
        assertThat(bookResponseDtoList.get(1).getAuthor()).isEqualTo("이진강");
    }

    @Test
    public void 책한권보기_테스트() {
        // given
        Long id = 1L;
        Book book = new Book(1L, "junit강의", "메타코딩");

        // stub
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        // when
        BookResponseDto bookResponseDto = bookService.책한권보기(id);

        // then
        assertThat(bookResponseDto.getTitle()).isEqualTo(book.getTitle());
        assertThat(bookResponseDto.getAuthor()).isEqualTo(book.getAuthor());
    }

    @Test
    public void 책한권보기_실페_테스트() {
        // given
        Long id = 1L;
        Book book = new Book(1L, "junit강의", "메타코딩");

        // stub
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> bookService.책한권보기(id))
                .isInstanceOf(Exception.class) // 부모 타입도 체크
                .isExactlyInstanceOf(RuntimeException.class) // 정확히 해당 예외만 체크
                .hasMessageContaining("해당 아이디를 찾을 수 없습니다.");
    }
    @Test
    public void 책수정하기_테스트() {
        // given
        Long id = 1L;
        BookSaveReqDto dto = new BookSaveReqDto("spring", "겟인데어");

        // stub
        Book book = new Book(1L, "junit강의", "메타코딩");
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        // when
        BookResponseDto bookResponseDto = bookService.책수정하기(id, dto);
        // update가 더티체킹으로 변경되기떄문에 외부에서 확인할 방법이 없어 리턴되게 코드를 변경

        //then
        assertThat(bookResponseDto.getTitle()).isEqualTo(dto.getTitle());
        assertThat(bookResponseDto.getAuthor()).isEqualTo(dto.getAuthor());
    }
}
