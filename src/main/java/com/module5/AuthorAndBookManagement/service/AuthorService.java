package com.module5.AuthorAndBookManagement.service;

import com.module5.AuthorAndBookManagement.dto.AuthorRequestDTO;
import com.module5.AuthorAndBookManagement.dto.AuthorResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AuthorService {

    AuthorResponseDTO createAuthor(AuthorRequestDTO dto);

    AuthorResponseDTO getAuthorById(Long id);

    List<AuthorResponseDTO> getAllAuthors();
    List<AuthorResponseDTO> createAuthorsBulk(
            List<AuthorRequestDTO> dtos);

    AuthorResponseDTO updateAuthor(Long id,
                                   AuthorRequestDTO dto);

    void deleteAuthor(Long id);
    byte[] exportAuthorsToCsv();

    void importAuthorsFromCsv(
            MultipartFile file);
}
