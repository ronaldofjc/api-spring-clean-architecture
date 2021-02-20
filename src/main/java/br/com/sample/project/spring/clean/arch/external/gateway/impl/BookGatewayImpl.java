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

import java.util.ArrayList;
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
    public Optional<Book> findById(final Long id) {
        final Optional<BookModel> bookModel;

        try {
            bookModel = repository.findById(id);
        } catch (final Exception e) {
            log.error("Error when trying to find book by id {} on database. Stack: {}", id, e);
            throw new GatewayException("Error when trying to find book by id on database" + e);
        }

        return bookModel.map(BookAdapter::fromModelToDomain);
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
        final List<BookModel> books = new ArrayList<>();
        final List<Book> bookList = new ArrayList<>();

        try {
            repository.findAll().forEach(books::add);
        } catch (final Exception e) {
            log.error("Error when trying to find all books on database. Stack: " + e);
            throw new GatewayException("Error when trying to find all books on database" + e);
        }

        books.forEach(book -> bookList.add(BookAdapter.fromModelToDomain(book)));
        return bookList;
    }

    @Override
    public Book update(final Book book) {
        final BookModel bookModel = BookAdapter.fromDomainToModelForUpdate(book);

        try {
            return BookAdapter.fromModelToDomain(repository.save(bookModel));
        } catch (final Exception e) {
            log.error("Error when trying to update book {} on database. Stack: {}", book.getTitle(), e);
            throw new GatewayException("Error when trying to update book on database" + e);
        }
    }

    @Override
    public void remove(final Book book) {
        final BookModel bookModel = BookAdapter.fromDomainToModelForUpdate(book);

        try {
            repository.save(bookModel);
        } catch (final Exception e) {
            log.error("Error when trying to update book {} on database. Stack: {}", book.getTitle(), e);
            throw new GatewayException("Error when trying to update book on database" + e);
        }
    }

    @Override
    public Long count() {
        try {
            return repository.count();
        } catch (final Exception e) {
            log.error("Error when trying to count books on database. Stack: " + e);
            throw new GatewayException("Error when trying to count books on database" + e);
        }
    }
}
