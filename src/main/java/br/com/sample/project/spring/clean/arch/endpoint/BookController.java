package br.com.sample.project.spring.clean.arch.endpoint;


import br.com.sample.project.spring.clean.arch.adapter.BookAdapter;
import br.com.sample.project.spring.clean.arch.endpoint.entity.CreateBookVM;
import br.com.sample.project.spring.clean.arch.exception.ControllerExceptionHandler;
import br.com.sample.project.spring.clean.arch.usecase.CreateBook;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/books", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class BookController extends ControllerExceptionHandler {

    private final CreateBook createBook;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @Validated @RequestBody final CreateBookVM createBookVM) {
        log.info("PAYLOAD - Create new book: {}", createBookVM.toString());
        createBook.execute(BookAdapter.fromVMToDomain(createBookVM));
    }

}
