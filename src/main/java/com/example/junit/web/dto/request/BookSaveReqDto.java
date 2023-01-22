package com.example.junit.web.dto.request;

import com.example.junit.domain.Book;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter // Controller에서 Setter가 호출되면서 DTO에 값이 세팅
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookSaveReqDto {
    @Size(min = 1, max = 50, message = "title size 문제")
    @NotBlank(message = "title은 필수 값")
    private String title;
    @Size(min = 2, max = 20, message = "author size 문제")
    @NotBlank(message = "author는 필수 값")
    private String author;

    public Book toEntity() {
        return Book.builder()
                .title(title)
                .author(author)
                .build();
    }
}
