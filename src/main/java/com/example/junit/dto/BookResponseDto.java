package com.example.junit.dto;

import com.example.junit.domain.Book;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @ToString
@Builder
public class BookResponseDto {
    private Long id;
    private String title;
    private String author;

//    public BookResponseDto toDto(Book bookPS) {
//        this.id = bookPS.getId();
//        this.title = bookPS.getTitle();
//        this.author = bookPS.getAuthor();
//        return this;
//    }
}
