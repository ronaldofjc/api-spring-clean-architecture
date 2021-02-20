package br.com.sample.project.spring.clean.arch.external.gateway.impl;

import br.com.sample.project.spring.clean.arch.adapter.BookAdapter;
import br.com.sample.project.spring.clean.arch.domain.Book;
import br.com.sample.project.spring.clean.arch.exception.GatewayException;
import br.com.sample.project.spring.clean.arch.external.database.repository.BookRepository;
import br.com.sample.project.spring.clean.arch.external.database.repository.model.BookModel;
import br.com.sample.project.spring.clean.arch.external.gateway.BookGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookGatewayImpl implements BookGateway {

    private final BookRepository repository;

    @Override
    public Book create(final Book book) {
        final BookModel bookModel = BookAdapter.fromDomainToModel(book);

        try {
            return BookAdapter.fromModelToDomain(repository.save(bookModel));
        } catch (final Exception e) {
            log.error("Error when trying to save new book {} on database. Stack: {}", book.getTitle(), e);
            throw new GatewayException("Error when trying to save new book on database" + e);
        }
    }

    @Override
    public Optional<Book> findByTitle(final String title) {
        final Optional<BookModel> bookModel;

        try {
            bookModel = repository.findByTitle(title);
        } catch (final Exception e) {
            log.error("Error when trying to find book {} on database. Stack: {}", title, e);
            throw new GatewayException("Error when trying to find book on database" + e);
        }

        return bookModel.map(BookAdapter::fromModelToDomain);
    }

    @Override
    public List<Book> findAll() {
        try {
            //final List<BookModel> bookModels = repository.findAll();
            return null;
        } catch (final Exception e) {
            log.error("Error when trying to find all books on database. Stack: " + e);
            throw new GatewayException("Error when trying to find all books on database" + e);
        }
    }
}
