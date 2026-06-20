package com.module5.AuthorAndBookManagement.serviceimpl;

import com.module5.AuthorAndBookManagement.dto.BookRequestDTO;
import com.module5.AuthorAndBookManagement.dto.BookResponseDTO;
import com.module5.AuthorAndBookManagement.entity.Author;
import com.module5.AuthorAndBookManagement.entity.Book;
import com.module5.AuthorAndBookManagement.exception.AuthorNotFoundException;
import com.module5.AuthorAndBookManagement.exception.BookNotFoundException;
import com.module5.AuthorAndBookManagement.repository.AuthorRepository;
import com.module5.AuthorAndBookManagement.repository.BookRepository;
import com.module5.AuthorAndBookManagement.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    public BookResponseDTO createBook(BookRequestDTO dto) {
        log.info("Creating book: {}", dto.getTitle());

        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() ->
                        new AuthorNotFoundException(
                                "Author not found"));

        Book book = Book.builder()
                .title(dto.getTitle())
                .isbn(dto.getIsbn())
                .price(dto.getPrice())
                .author(author)
                .build();

        Book savedBook = bookRepository.save(book);
        log.info("Book created successfully with id: {}",
                savedBook.getId());

        return mapToResponse(savedBook);
    }

    @Override
    public BookResponseDTO getBookById(Long id) {
        log.info("Fetching book with id: {}", id);

        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new BookNotFoundException(
                                "Book not found with id: " + id));

        return mapToResponse(book);
    }

    @Override
    public List<BookResponseDTO> getAllBooks() {

        return bookRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public BookResponseDTO updateBook(Long id,
                                      BookRequestDTO dto) {
        log.info("Updating book with id: {}", id);

        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new BookNotFoundException(
                                "Book not found with id: " + id));

        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() ->
                        new AuthorNotFoundException(
                                "Author not found"));

        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setPrice(dto.getPrice());
        book.setAuthor(author);

        return mapToResponse(bookRepository.save(book));

    }

    @Override
    public void deleteBook(Long id) {
        log.warn("Deleting book with id: {}", id);

        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new BookNotFoundException(
                                "Book not found with id: " + id));

        bookRepository.delete(book);
        log.info("Book deleted successfully");
    }

    private BookResponseDTO mapToResponse(Book book) {

        return BookResponseDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .price(book.getPrice())
                .authorId(book.getAuthor().getId())
                .authorName(book.getAuthor().getName())
                .build();
    }

    @Override
    public List<BookResponseDTO> createBooksBulk(
            List<BookRequestDTO> dtos) {

        List<Book> books = dtos.stream()
                .map(dto -> {

                    Author author =
                            authorRepository.findById(
                                            dto.getAuthorId())
                                    .orElseThrow(() ->
                                            new AuthorNotFoundException(
                                                    "Author not found"));

                    return Book.builder()
                            .title(dto.getTitle())
                            .isbn(dto.getIsbn())
                            .price(dto.getPrice())
                            .author(author)
                            .build();
                })
                .toList();

        List<Book> savedBooks =
                bookRepository.saveAll(books);

        return savedBooks.stream()
                .map(this::mapToResponse)
                .toList();
    }
    @Override
    public byte[] exportBooksToCsv() {

        List<Book> books = bookRepository.findAll();

        try (
                ByteArrayOutputStream out =
                        new ByteArrayOutputStream();

                CSVPrinter csvPrinter =
                        new CSVPrinter(
                                new PrintWriter(out),
                                CSVFormat.DEFAULT.withHeader(
                                        "ID",
                                        "TITLE",
                                        "ISBN",
                                        "PRICE",
                                        "AUTHOR_ID"))
        ) {

            for (Book book : books) {

                csvPrinter.printRecord(
                        book.getId(),
                        book.getTitle(),
                        book.getIsbn(),
                        book.getPrice(),
                        book.getAuthor().getId()
                );
            }

            csvPrinter.flush();

            return out.toByteArray();

        } catch (IOException e) {
            log.error("Error while exporting books CSV", e);

            throw new RuntimeException(
                    "Failed to export books CSV");
        }
    }
    @Override
    public void importBooksFromCsv(
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

                Long authorId =
                        Long.parseLong(
                                record.get("AUTHOR_ID"));

                Author author =
                        authorRepository.findById(authorId)
                                .orElseThrow(() ->
                                        new AuthorNotFoundException(
                                                "Author not found with id: "
                                                        + authorId));

                Book book = Book.builder()
                        .title(record.get("TITLE"))
                        .isbn(record.get("ISBN"))
                        .price(Double.parseDouble(
                                record.get("PRICE")))
                        .author(author)
                        .build();

                bookRepository.save(book);
            }

        } catch (IOException e) {
            log.error("Error while importing books CSV", e);

            throw new RuntimeException(
                    "Failed to import books CSV");
        }
    }

}
