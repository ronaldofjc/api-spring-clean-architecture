package br.com.sample.project.spring.clean.arch.fixtures;

import br.com.sample.project.spring.clean.arch.domain.Book;

import java.time.ZonedDateTime;

public class BookFixture {

    public static Book gimmeBasicBook() {
        return Book.builder()
                .id(1L)
                .title("Title test")
                .author("Author test")
                .pages(100)
                .active(true)
                .creationTime(ZonedDateTime.now())
                .updateTime(ZonedDateTime.now())
                .build();
    }
}
