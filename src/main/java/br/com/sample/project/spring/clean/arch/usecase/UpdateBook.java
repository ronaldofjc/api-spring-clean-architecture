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
public class UpdateBook {

    private final BookGateway gateway;
    private final FindBookById findBookById;

    public Book execute(final Book updateBook) {
        final Long id = updateBook.getId();

        final Book book = findBookById.execute(id);

        if (!updateBook.getTitle().equals(book.getTitle())) {
            final Optional<Book> bookOptional = gateway.findByTitle(book.getTitle());

            if (bookOptional.isPresent()) {
                log.error("Book already exists. Title: " + bookOptional.get().getTitle());
                throw new BusinessException("Book with name " + bookOptional.get().getTitle() + " already exists!");
            }
        }

        updateBook.setCreationTime(book.getCreationTime());

        final Book bookUpdated = gateway.update(updateBook);
        log.info("Book with title {} was updated", bookUpdated.getTitle());
        return bookUpdated;
    }

}
