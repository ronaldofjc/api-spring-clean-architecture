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
public class FindBookById {

    private final BookGateway gateway;

    public Book execute(final Long id) {
        return gateway.findById(id)
                .orElseThrow(() -> {
                    log.error("Book with id {} not found on database", id);
                    throw new EntityNotFoundException("Book with id " + id + " not found on database");
                });
    }
}
