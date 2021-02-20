package br.com.sample.project.spring.clean.arch.endpoint;


import br.com.sample.project.spring.clean.arch.adapter.BookAdapter;
import br.com.sample.project.spring.clean.arch.domain.Book;
import br.com.sample.project.spring.clean.arch.endpoint.entity.CreateBookVM;
import br.com.sample.project.spring.clean.arch.endpoint.entity.UpdateBookVM;
import br.com.sample.project.spring.clean.arch.exception.ControllerExceptionHandler;
import br.com.sample.project.spring.clean.arch.usecase.*;
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
    public void create(@Valid @Validated @RequestBody final CreateBookVM createBookVM) {
        log.info("PAYLOAD - Create new book: {}", createBookVM.toString());
        createBook.execute(BookAdapter.fromVMToDomain(createBookVM));
    }

    @GetMapping
    public List<Book> findAll() {
        return findAllBooks.execute();
    }

    @PutMapping
    public Book update(@Valid @RequestBody final UpdateBookVM updateBookVM) {
        log.info("PAYLOAD - Update book: {}", updateBookVM.toString());
        return updateBook.execute(BookAdapter.fromVMToDomainForUpdate(updateBookVM));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable final Long id) {
        log.info("PAYLOAD - Remove book with id {}", id);
        removeBook.execute(id);
    }

    @GetMapping(value = "/{id}")
    public Book findById(@PathVariable final Long id) {
        log.info("PAYLOAD - Find book by id {}", id);
        return findBookById.execute(id);
    }

    @GetMapping(value = "/title/{title}")
    public Book findByTitle(@PathVariable final String title) {
        log.info("PAYLOAD - Find book by title {}", title);
        return findBookByTitle.execute(title);
    }
    
    @GetMapping(value = "/count")
    public Long count() {
        return countBooks.execute();
    }
}
