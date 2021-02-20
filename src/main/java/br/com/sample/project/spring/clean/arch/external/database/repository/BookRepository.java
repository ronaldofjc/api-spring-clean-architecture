package br.com.sample.project.spring.clean.arch.external.database.repository;

import br.com.sample.project.spring.clean.arch.external.database.repository.model.BookModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<BookModel, Long> {

    Optional<BookModel> findByTitle(String title);
}
