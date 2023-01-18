package com.example.junit.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest // DB와 관련된 컴포넌트만 메모리에 로딩
public class BookRepositoryTest {

    @Autowired // DI
    private BookRepository bookRepository;

//    @BeforeAll // 테스트 시작 전에 한번만 실행
    @BeforeEach // 각 테스트 시작전에 한번 씩 실행
    public void 데이터준비() {
        String title = "junit5";
        String author = "lela";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();
        Book saveBook = bookRepository.save(book);
        System.out.println("saveBook :" + saveBook.toString());
    }
    // @BeforeEach는 아래와 같이 트랜잭션의 범위가 된다.
    // [데이터준비() + 1 책등록] (T), [데이터준비() + 2 책목록보기] (T) -> 사이즈 1

    // 1. 책 등록
    @Test
    public void 책등록_test() {
        // given (데이터 준비)
        String title = "junit5";
        String author = "simpson";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();

        // when (테스트 실행)
        Book bookPS = bookRepository.save(book);

        // then (검증)
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());
    }// 트랜잭션 롤백

    // 2. 책 목록보기
    @Test
    public void 책목록보기_test() {
        // given
        String title = "junit5";
        String author = "lela";

        // when
        List<Book> bookPS = bookRepository.findAll();

        System.out.println("bookPS : " + bookPS.size());
        // then
        Assertions.assertEquals(title, bookPS.get(0).getTitle());
        Assertions.assertEquals(author, bookPS.get(0).getAuthor());
    }

    // 3. 책 한권 보기
    @Test
    public void 책한권보기_test() {
        //given
        String title = "junit5";
        String author = "lela";

        //when
        System.out.println("size : " + bookRepository.findAll().size());

        Book bookPS = bookRepository.findById(4L).get(); // ID가 다를수 있으므로 조심해야 함

        System.out.println("size : " + bookRepository.findAll().size());

        //then
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());
    }

    // 4. 책 수정

    // 5. 책 삭제

}
