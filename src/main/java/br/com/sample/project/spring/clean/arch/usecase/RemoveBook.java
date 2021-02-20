package br.com.sample.project.spring.clean.arch.usecase;

import br.com.sample.project.spring.clean.arch.domain.Book;
import br.com.sample.project.spring.clean.arch.external.gateway.BookGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RemoveBook {

    private final BookGateway gateway;
    private final FindBookById findBookById;

    public void execute(final Long id) {
        final Book book = findBookById.execute(id);

        book.setActive(false);
        gateway.update(book);
        log.info("Book with title {} has been inactivated", book.getTitle());
    }
}
