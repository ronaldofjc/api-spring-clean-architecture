package br.com.sample.project.spring.clean.arch.external.gateway;

import br.com.sample.project.spring.clean.arch.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookGateway {
    Book create(Book book);

    Optional<Book> findByTitle(String title);

    List<Book> findAll();
}
