package com.example.junit.web.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter @Builder
public class BookListResDto {
    List<BookResDto> items;
}
