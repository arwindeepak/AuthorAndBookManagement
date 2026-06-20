package com.module5.AuthorAndBookManagement.controller;

import com.module5.AuthorAndBookManagement.dto.BookRequestDTO;
import com.module5.AuthorAndBookManagement.dto.BookResponseDTO;
import com.module5.AuthorAndBookManagement.service.BookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;





@Tag(
        name = "Book Management",
        description = "CRUD operations for books"
)

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponseDTO createBook(
            @Valid @RequestBody BookRequestDTO dto) {

        return bookService.createBook(dto);
    }

    @GetMapping
    public List<BookResponseDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookResponseDTO getBookById(
            @PathVariable Long id) {

        return bookService.getBookById(id);
    }

    @PutMapping("/{id}")
    public BookResponseDTO updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookRequestDTO dto) {

        return bookService.updateBook(id, dto);
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id) {

        bookService.deleteBook(id);

        return "Book deleted successfully";
    }
    @PostMapping("/bulk")
    @ResponseStatus(HttpStatus.CREATED)
    public List<BookResponseDTO> createBooksBulk(
            @RequestBody List<BookRequestDTO> dtos) {

        return bookService.createBooksBulk(dtos);
    }
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportBooks() {

        byte[] csvData =
                bookService.exportBooksToCsv();

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=books.csv")
                .contentType(
                        MediaType.APPLICATION_OCTET_STREAM)
                .body(csvData);
    }
    @PostMapping(
            value = "/import",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @Operation(summary = "Import books from CSV")
    public String importBooks(

            @Parameter(
                    description = "CSV file",
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(
                                    type = "string",
                                    format = "binary"
                            )
                    )
            )
            @RequestParam("file")
            MultipartFile file) {

        bookService.importBooksFromCsv(file);

        return "Books imported successfully";
    }
}