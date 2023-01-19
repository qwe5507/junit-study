package com.example.junit.domain;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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

    // 트랜잭션이 종료되어도 auto_increment는 초기화가 안된다.
    // 해결방법
    // 1. @AfterEach에서 모든 테스트 끝날때마다 auto increment를 초기화하는 ALTER문 실행
    // 2. @Sql에 쿼리문 파일 지정해서 해당 테스트 시작할때 마다 쿼리문 실행

    // 1. 책 등록
    @Order(1) // @TEST 메서드는 순서보장이 안되기 때문에 순서보장이 필요하면 @Order()로 순서 지정
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
    @Sql("classpath:db/tableInit.sql") // 해당 메서드가 실행되기 직전에 실행 된다.
    @Test
    public void 책한권보기_test() {
        //given
        String title = "junit5";
        String author = "lela";

        //when
        System.out.println("size : " + bookRepository.findAll().size());

        Book bookPS = bookRepository.findById(1L).get();

        System.out.println("size : " + bookRepository.findAll().size());

        //then
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());
    }

    // 4. 책 삭제
    @Sql("classpath:db/tableInit.sql") // 해당 메서드가 실행되기 직전에 실행 된다.
    @Test
    public void 책삭제_test() {
        // given
        Long id = 1L;

        // when
        bookRepository.deleteById(id);

        // then
        //삭제 후 거래가 있으면 테스트 실패
        assertFalse(bookRepository.findById(id).isPresent());
    }

    // 1. junit5, lela
    // 5. 책 수정
    @Sql("classpath:db/tableInit.sql") // 해당 메서드가 실행되기 직전에 실행 된다.
    @Test
    public void 책수정_test() {
        // given
        Long id = 1L;
        String title = "junit77";
        String author = "norma";
        Book book = new Book(id, title, author);

        // when
        // id가 있기 때문에 insert가 아닌  update
        Book bookPS = bookRepository.save(book);

//        bookRepository.findAll().stream()
//                .forEach(books ->{
//                    System.out.println(books.toString());
//                    System.out.println("===================");
//                });

        // then
        assertEquals(id, bookPS.getId());
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());
    }

}
