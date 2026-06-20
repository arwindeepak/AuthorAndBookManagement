package com.module5.AuthorAndBookManagement.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponseDTO {

    private Long id;

    private String title;

    private String isbn;

    private Double price;

    private Long authorId;

    private String authorName;
}