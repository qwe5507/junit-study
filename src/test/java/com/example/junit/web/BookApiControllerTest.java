package com.example.junit.web;


import com.example.junit.domain.Book;
import com.example.junit.domain.BookRepository;
import com.example.junit.web.dto.request.BookSaveReqDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

// 컨트롤러는 통합테스트 (C, S, R)
// 컨트롤러만 테스트하는 것이 아님
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookApiControllerTest {
    private static ObjectMapper objectMapper;
    private static HttpHeaders headers;

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private TestRestTemplate rt;

    @BeforeAll // 테스트 시작 전에 한번만 실행
    public static void init() {
        objectMapper = new ObjectMapper();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @BeforeEach // 각 테스트 시작전에 한번 씩 실행
    public void 데이터준비() {
        String title = "junit5";
        String author = "lela";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();
        Book saveBook = bookRepository.save(book);
        System.out.println("saveBook :" + saveBook);
    }

    @Test
    public void saveBook_test() throws JsonProcessingException {
        // given
        BookSaveReqDto bookSaveReqDto = new BookSaveReqDto("스프링1강", "겟인데어");
        // 클라이언트가 한 요청은 json데이터를 디스패처 서블릿이 받아 객체화해서 컨트롤러로 던져 주기 떄문에
        // given은 객체가 아닌 json String이어야 한다.
        // TODO 근데그냥 obj날려도 되는데???
        String body = objectMapper.writeValueAsString(bookSaveReqDto);

        // when
        HttpEntity<BookSaveReqDto> request = new HttpEntity<>(bookSaveReqDto, headers);
        ResponseEntity<String> response = rt.exchange("/api/v1/book", HttpMethod.POST, request, String.class);
        //위의 ResponseEntity의 제네릭을 String으로 해줘야 JsonPath가 제대로 인식함.
        //object로 하면 json을 읽어도 title, author도 object가 된다.

        // then
        DocumentContext dc = JsonPath.parse(response.getBody());
        String title = dc.read("$.body.title");
        String author = dc.read("$.body.author");

        assertThat(bookSaveReqDto.getTitle()).isEqualTo(title);
        assertThat(bookSaveReqDto.getAuthor()).isEqualTo(author);
    }

    @Test
    public void getBookList_test() {
        // given
        // when
        HttpEntity<?> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = rt.exchange("/api/v1/book", HttpMethod.GET, request, String.class);

        // then
        DocumentContext dc = JsonPath.parse(response.getBody());
        Integer code = dc.read("$.code");
        String title = dc.read("$.body.items[0].title");

        assertThat(1).isEqualTo(code);
        assertThat("junit5").isEqualTo(title);
    }
}
