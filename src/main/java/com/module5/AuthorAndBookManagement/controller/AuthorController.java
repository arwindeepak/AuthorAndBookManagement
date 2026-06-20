package com.module5.AuthorAndBookManagement.controller;

import com.module5.AuthorAndBookManagement.dto.AuthorRequestDTO;
import com.module5.AuthorAndBookManagement.dto.AuthorResponseDTO;
import com.module5.AuthorAndBookManagement.service.AuthorService;
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
        name = "Author Management",
        description = "CRUD operations for authors"
)
@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @Operation(
            summary = "Create Author",
            description = "Creates a new author"
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorResponseDTO createAuthor(
            @Valid @RequestBody AuthorRequestDTO dto) {

        return authorService.createAuthor(dto);
    }
    @Operation(
            summary = "Get All Authors",
            description = "Get All Authors"
    )
    @GetMapping
    public List<AuthorResponseDTO> getAllAuthors() {
        return authorService.getAllAuthors();
    }
    @Operation(
            summary = "Get Author By Id",
            description = "Get Author By Id"
    )
    @GetMapping("/{id}")
    public AuthorResponseDTO getAuthorById(
            @PathVariable Long id) {

        return authorService.getAuthorById(id);
    }
    @Operation(
            summary = "update Author",
            description = "update the author"
    )
    @PutMapping("/{id}")
    public AuthorResponseDTO updateAuthor(
            @PathVariable Long id,
            @Valid @RequestBody AuthorRequestDTO dto) {

        return authorService.updateAuthor(id, dto);
    }
    @Operation(
            summary = "Delete Author",
            description = "Delete the  author"
    )
    @DeleteMapping("/{id}")
    public String deleteAuthor(@PathVariable Long id) {

        authorService.deleteAuthor(id);

        return "Author deleted successfully";
    }
    @Operation(
            summary = "Get  Authors",
            description = "Get All authors"
    )
    @PostMapping("/bulk")
    @ResponseStatus(HttpStatus.CREATED)
    public List<AuthorResponseDTO> createAuthorsBulk(
            @RequestBody List<AuthorRequestDTO> dtos) {

        return authorService.createAuthorsBulk(dtos);
    }
    @Operation(
            summary = "Export  Authors",
            description = "Export authors"
    )
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportAuthors() {

        byte[] csvData =
                authorService.exportAuthorsToCsv();

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=authors.csv")
                .contentType(
                        MediaType.APPLICATION_OCTET_STREAM)
                .body(csvData);
    }

    @PostMapping(
            value = "/import",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Import authors from CSV")
    public String importAuthors(
            @Parameter(
                    description = "CSV file",
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(type = "string", format = "binary")
                    )
            )
            @RequestParam("file") MultipartFile file) {

        authorService.importAuthorsFromCsv(file);

        return "Authors imported successfully";
    }
}
