package br.com.sample.project.spring.clean.arch.external.gateway;

import br.com.sample.project.spring.clean.arch.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookGateway {
    Book create(Book book);

    Optional<Book> findById(Long id);

    Optional<Book> findByTitle(String title);

    List<Book> findAll();

    Book update(Book book);

    void remove(Book book);

    Long count();
}
