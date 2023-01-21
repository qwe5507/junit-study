package com.example.junit.dto.request;

import com.example.junit.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter // Controller에서 Setter가 호출되면서 DTO에 값이 세팅
@AllArgsConstructor
public class BookSaveReqDto {
    @Size(min = 1, max = 50)
    @NotBlank
    private String title;
    @Size(min = 2, max = 20)
    @NotBlank
    private String author;

    public Book toEntity() {
        return Book.builder()
                .title(title)
                .author(author)
                .build();
    }
}
