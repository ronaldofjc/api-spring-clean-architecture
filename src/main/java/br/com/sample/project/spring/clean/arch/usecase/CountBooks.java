package br.com.sample.project.spring.clean.arch.usecase;

import br.com.sample.project.spring.clean.arch.external.gateway.BookGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CountBooks {

    private final BookGateway gateway;
    
    public Long execute() {
        Long countBooks = gateway.count();
        log.info("Found {} books on database", countBooks);
        return countBooks;
    }
}
