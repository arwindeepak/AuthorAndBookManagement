package com.module5.AuthorAndBookManagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Author name is required")
    @Column(nullable = false)
    private String name;

    @Email(message = "Invalid email format")
    @Column(unique = true)
    private String email;

    private String country;

    @OneToMany(mappedBy = "author",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Book> books = new ArrayList<>();
}
