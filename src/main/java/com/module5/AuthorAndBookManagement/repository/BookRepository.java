package com.module5.AuthorAndBookManagement.repository;

import com.module5.AuthorAndBookManagement.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository
        extends JpaRepository<Book, Long> {
}
