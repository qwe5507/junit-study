package com.example.junit.web.dto.response;

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
