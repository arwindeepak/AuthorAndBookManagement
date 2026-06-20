package com.module5.AuthorAndBookManagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequestDTO {

    @NotBlank
    private String title;

    private String isbn;

    @NotNull
    private Double price;

    @NotNull
    private Long authorId;
}
