package com.module5.AuthorAndBookManagement.serviceimpl;

import com.module5.AuthorAndBookManagement.dto.AuthorRequestDTO;
import com.module5.AuthorAndBookManagement.dto.AuthorResponseDTO;
import com.module5.AuthorAndBookManagement.entity.Author;
import com.module5.AuthorAndBookManagement.exception.AuthorNotFoundException;
import com.module5.AuthorAndBookManagement.exception.DuplicateAuthorException;
import com.module5.AuthorAndBookManagement.repository.AuthorRepository;
import com.module5.AuthorAndBookManagement.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public AuthorResponseDTO createAuthor(AuthorRequestDTO dto) {

        if (authorRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateAuthorException(
                    "Author already exists with email: " + dto.getEmail());
        }

        Author author = Author.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .country(dto.getCountry())
                .build();
        log.info("Creating author with email: {}", dto.getEmail());

        Author savedAuthor = authorRepository.save(author);
        log.info("Author created successfully with id: {}",
                savedAuthor.getId());

        return AuthorResponseDTO.builder()
                .id(savedAuthor.getId())
                .name(savedAuthor.getName())
                .email(savedAuthor.getEmail())
                .country(savedAuthor.getCountry())
                .build();
    }

    @Override
    public AuthorResponseDTO getAuthorById(Long id) {
        log.info("Fetching author with id: {}", id);
        Author author = authorRepository.findById(id)
                .orElseThrow(() ->
                        new AuthorNotFoundException(
                                "Author not found with id: " + id));

        return AuthorResponseDTO.builder()
                .id(author.getId())
                .name(author.getName())
                .email(author.getEmail())
                .country(author.getCountry())
                .build();
    }

    @Override
    public List<AuthorResponseDTO> getAllAuthors() {

        return authorRepository.findAll()
                .stream()
                .map(author -> AuthorResponseDTO.builder()
                        .id(author.getId())
                        .name(author.getName())
                        .email(author.getEmail())
                        .country(author.getCountry())
                        .build())
                .toList();
    }

    @Override
    public AuthorResponseDTO updateAuthor(Long id,
                                          AuthorRequestDTO dto) {
        log.info("Updating author with id: {}", id);

        Author author = authorRepository.findById(id)
                .orElseThrow(() ->
                        new AuthorNotFoundException(
                                "Author not found with id: " + id));

        author.setName(dto.getName());
        author.setEmail(dto.getEmail());
        author.setCountry(dto.getCountry());

        Author updatedAuthor = authorRepository.save(author);
        log.info("Author updated successfully with id: {}", id);

        return AuthorResponseDTO.builder()
                .id(updatedAuthor.getId())
                .name(updatedAuthor.getName())
                .email(updatedAuthor.getEmail())
                .country(updatedAuthor.getCountry())
                .build();
    }

    @Override
    public void deleteAuthor(Long id) {
        log.warn("Deleting author with id: {}", id);

        Author author = authorRepository.findById(id)
                .orElseThrow(() ->
                        new AuthorNotFoundException(
                                "Author not found with id: " + id));

        authorRepository.delete(author);
        log.info("Author deleted successfully");
    }
    @Override
    public List<AuthorResponseDTO> createAuthorsBulk(
            List<AuthorRequestDTO> dtos) {

        List<Author> authors = dtos.stream()
                .map(dto -> {

                    if(authorRepository.existsByEmail(
                            dto.getEmail())) {

                        throw new DuplicateAuthorException(
                                "Author already exists with email: "
                                        + dto.getEmail());
                    }

                    return Author.builder()
                            .name(dto.getName())
                            .email(dto.getEmail())
                            .country(dto.getCountry())
                            .build();
                })
                .toList();

        List<Author> savedAuthors =
                authorRepository.saveAll(authors);

        return savedAuthors.stream()
                .map(author ->
                        AuthorResponseDTO.builder()
                                .id(author.getId())
                                .name(author.getName())
                                .email(author.getEmail())
                                .country(author.getCountry())
                                .build())
                .toList();
    }
    @Override
    public byte[] exportAuthorsToCsv() {

        List<Author> authors =
                authorRepository.findAll();

        try (
                ByteArrayOutputStream out =
                        new ByteArrayOutputStream();

                CSVPrinter csvPrinter =
                        new CSVPrinter(
                                new PrintWriter(out),
                                CSVFormat.DEFAULT
                                        .withHeader(
                                                "ID",
                                                "NAME",
                                                "EMAIL",
                                                "COUNTRY"))
        ) {

            for (Author author : authors) {

                csvPrinter.printRecord(
                        author.getId(),
                        author.getName(),
                        author.getEmail(),
                        author.getCountry()
                );
            }

            csvPrinter.flush();

            return out.toByteArray();

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to export CSV");
        }
    }
    @Override
    public void importAuthorsFromCsv(
            MultipartFile file) {

        try (
                BufferedReader reader =
                        new BufferedReader(
                                new InputStreamReader(
                                        file.getInputStream(),
                                        StandardCharsets.UTF_8))
        ) {

            Iterable<CSVRecord> records =
                    CSVFormat.DEFAULT
                            .withFirstRecordAsHeader()
                            .parse(reader);

            for (CSVRecord record : records) {

                String email = record.get("EMAIL");

                if(authorRepository.existsByEmail(email)) {
                    continue;
                }

                Author author = Author.builder()
                        .name(record.get("NAME"))
                        .email(email)
                        .country(record.get("COUNTRY"))
                        .build();

                authorRepository.save(author);
            }

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to import CSV");
        }
    }
}
