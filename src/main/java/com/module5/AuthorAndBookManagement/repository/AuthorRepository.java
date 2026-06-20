package com.module5.AuthorAndBookManagement.repository;

import com.module5.AuthorAndBookManagement.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository
        extends JpaRepository<Author, Long> {

    boolean existsByEmail(String email);
}
