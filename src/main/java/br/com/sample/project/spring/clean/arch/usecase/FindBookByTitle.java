package br.com.sample.project.spring.clean.arch.usecase;

import br.com.sample.project.spring.clean.arch.domain.Book;
import br.com.sample.project.spring.clean.arch.exception.EntityNotFoundException;
import br.com.sample.project.spring.clean.arch.external.gateway.BookGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FindBookByTitle {

    private final BookGateway gateway;

    public Book execute(final String title) {
        return gateway.findByTitle(title)
                .orElseThrow(() -> {
                    log.error("Book with title {} not found on database", title);
                    throw new EntityNotFoundException("Book with title " + title + " not found on database");
                });
    }
}
