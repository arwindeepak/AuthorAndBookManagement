package com.module5.AuthorAndBookManagement.service;

import com.module5.AuthorAndBookManagement.dto.BookRequestDTO;
import com.module5.AuthorAndBookManagement.dto.BookResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {

    BookResponseDTO createBook(BookRequestDTO dto);

    BookResponseDTO getBookById(Long id);

    List<BookResponseDTO> getAllBooks();

    BookResponseDTO updateBook(Long id,
                               BookRequestDTO dto);
    List<BookResponseDTO> createBooksBulk(
            List<BookRequestDTO> dtos);

    void deleteBook(Long id);
    byte[] exportBooksToCsv();

    void importBooksFromCsv(
            MultipartFile file);
}