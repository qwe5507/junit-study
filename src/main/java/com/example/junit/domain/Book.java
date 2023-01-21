package com.example.junit.domain;

import com.example.junit.dto.response.BookResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity @ToString
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    private String title;
    @Column(length = 20, nullable = false)
    private String author;

    @Builder
    public Book(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public void update(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public BookResponseDto toDto() {
        return BookResponseDto.builder()
                .id(this.id)
                .title(this.title)
                .author(this.author)
                .build();
    }
}
