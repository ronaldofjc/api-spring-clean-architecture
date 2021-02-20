package br.com.sample.project.spring.clean.arch.endpoint;


import br.com.sample.project.spring.clean.arch.adapter.BookAdapter;
import br.com.sample.project.spring.clean.arch.domain.Book;
import br.com.sample.project.spring.clean.arch.endpoint.entity.CreateBookVM;
import br.com.sample.project.spring.clean.arch.endpoint.entity.UpdateBookVM;
import br.com.sample.project.spring.clean.arch.exception.ControllerExceptionHandler;
import br.com.sample.project.spring.clean.arch.usecase.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@Tag(name = "Books")
@RequiredArgsConstructor
@RequestMapping(path = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController extends ControllerExceptionHandler {

    private final CreateBook createBook;
    private final FindAllBooks findAllBooks;
    private final UpdateBook updateBook;
    private final RemoveBook removeBook;
    private final FindBookById findBookById;
    private final FindBookByTitle findBookByTitle;
    private final CountBooks countBooks;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Realiza a criação de um novo livro")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Resource created",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "422", description = "Business exception", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
            @ApiResponse(responseCode = "503", description = "Gateway exception", content = @Content)})
    public void create(@Valid @Validated @RequestBody final CreateBookVM createBookVM) {
        log.info("PAYLOAD - Create new book: {}", createBookVM.toString());
        createBook.execute(BookAdapter.fromVMToDomain(createBookVM));
    }

    @GetMapping
    @Operation(summary = "Retorna a lista de livros")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Status Ok",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
            @ApiResponse(responseCode = "503", description = "Gateway exception", content = @Content)})
    public List<Book> findAll() {
        return findAllBooks.execute();
    }

    @PutMapping
    @Operation(summary = "Realiza a atualização de um livro")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Resource updated",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            @ApiResponse(responseCode = "422", description = "Business exception", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
            @ApiResponse(responseCode = "503", description = "Gateway exception", content = @Content)})
    public Book update(@Valid @RequestBody final UpdateBookVM updateBookVM) {
        log.info("PAYLOAD - Update book: {}", updateBookVM.toString());
        return updateBook.execute(BookAdapter.fromVMToDomainForUpdate(updateBookVM));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Realiza a remoção de um livro pelo id")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "No content",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            @ApiResponse(responseCode = "422", description = "Business exception", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
            @ApiResponse(responseCode = "503", description = "Gateway exception", content = @Content)})
    public void remove(@PathVariable final Long id) {
        log.info("PAYLOAD - Remove book with id {}", id);
        removeBook.execute(id);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Realiza a busca de um livro pelo id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Return Ok",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            @ApiResponse(responseCode = "422", description = "Business exception", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
            @ApiResponse(responseCode = "503", description = "Gateway exception", content = @Content)})
    public Book findById(@PathVariable final Long id) {
        log.info("PAYLOAD - Find book by id {}", id);
        return findBookById.execute(id);
    }

    @GetMapping(value = "/title/{title}")
    @Operation(summary = "Realiza a busca de um livro pelo título")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Return Ok",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            @ApiResponse(responseCode = "422", description = "Business exception", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
            @ApiResponse(responseCode = "503", description = "Gateway exception", content = @Content)})
    public Book findByTitle(@PathVariable final String title) {
        log.info("PAYLOAD - Find book by title {}", title);
        return findBookByTitle.execute(title);
    }

    @GetMapping(value = "/count")
    @Operation(summary = "Retorna a quantidade de livros cadastrados")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Return Ok",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
            @ApiResponse(responseCode = "503", description = "Gateway exception", content = @Content)})
    public Long count() {
        return countBooks.execute();
    }
}
