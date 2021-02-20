package br.com.sample.project.spring.clean.arch.usecase;

import br.com.sample.project.spring.clean.arch.domain.Book;
import br.com.sample.project.spring.clean.arch.exception.BusinessException;
import br.com.sample.project.spring.clean.arch.external.gateway.BookGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateBook {

    private final BookGateway gateway;

    public void execute(final Book book) {
        final Optional<Book> bookOptional = gateway.findByTitle(book.getTitle());

        if (bookOptional.isPresent()) {
            log.error("Book already exists. Title: " + bookOptional.get().getTitle());
            throw new BusinessException("Book with name " + bookOptional.get().getTitle() + " already exists!");
        }

        final Book newBook = gateway.create(book);
        log.info("Book with title {} was created", newBook.getTitle());
    }

}
