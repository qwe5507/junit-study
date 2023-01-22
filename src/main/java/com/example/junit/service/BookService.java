package com.example.junit.service;

import com.example.junit.domain.Book;
import com.example.junit.domain.BookRepository;
import com.example.junit.web.dto.response.BookListResDto;
import com.example.junit.web.dto.response.BookResDto;
import com.example.junit.web.dto.request.BookSaveReqDto;
import com.example.junit.util.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final MailSender mailSender;

    // 1. 책 등록
    @Transactional(rollbackFor = RuntimeException.class)
    public BookResDto 책등록하기(BookSaveReqDto dto) {
        Book bookPS = bookRepository.save(dto.toEntity());

        if (bookPS != null) {
            //메일보내기 메서드 호출
            if (!mailSender.send()) {
                throw new RuntimeException("메일이 전송되지 않았습니다.");
            }
        }

        return bookPS.toDto();
    }

    // 2. 책 목록 보기
    public BookListResDto 책목록보기() {
        List<BookResDto> dtos = bookRepository.findAll().stream()
                // 1. 정상, new가 두 번 실행 된다.
//                .map((bookPS) -> new BookResponseDto().toDto(bookPS))
                // 2. 비 정상 new는 한번만 실행되고, toDto가 여러번 실행 되는 거임
//                .map(new BookResponseDto()::toDto)
                // 3.
                .map(Book::toDto)
                // .map((bookPS) -> bookPS.toDto()) 이것과 같음
                .collect(Collectors.toList());
        // 2번은 아래 코드와 동일한 거임.
        //        BookResponseDto responseDto = new BookResponseDto();
//        return bookRepository.findAll().stream()
//                .map((bookPS) -> responseDto.toDto(bookPS))
//                .map(responseDto::toDto)
//                .collect(Collectors.toList());
        BookListResDto bookListResDto = BookListResDto.builder().items(dtos).build();
        return bookListResDto;
    }

    // 3. 책 한권 보기
    public BookResDto 책한권보기(Long id) {
        Book bookPS = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 아이디를 찾을 수 없습니다."));
        return bookPS.toDto();
    }

    // 4. 책 삭제
    @Transactional(rollbackFor = RuntimeException.class)
    public void 책삭제하기(Long id) {
        bookRepository.deleteById(id);
        // 삭제할 id를 못찾았다고 DB에서 오류가 발생하진 않지만,
        // Spring에서 IllegalArgumentException를 발생시킴.
    }
    // 책 삭제는 서비스레이어에서 하는일이 없기 떄문에, 테스트코드 필요 없음.

    // 5. 책 수정
    @Transactional(rollbackFor = RuntimeException.class)
    public BookResDto 책수정하기(Long id, BookSaveReqDto dto) {
        Book bookPS = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 아이디를 찾을 수 없습니다."));

        bookPS.update(dto.getTitle(), dto.getAuthor());
        // 더티체킹으로 flush
        return bookPS.toDto();
    }
}
