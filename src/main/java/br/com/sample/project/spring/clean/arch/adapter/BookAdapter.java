package br.com.sample.project.spring.clean.arch.adapter;

import br.com.sample.project.spring.clean.arch.domain.Book;
import br.com.sample.project.spring.clean.arch.endpoint.entity.CreateBookVM;
import br.com.sample.project.spring.clean.arch.endpoint.entity.UpdateBookVM;
import br.com.sample.project.spring.clean.arch.external.database.repository.model.BookModel;

import java.time.ZonedDateTime;

public class BookAdapter {

    private BookAdapter() {
        throw new IllegalStateException("Utility class");
    }

    public static BookModel fromDomainToModel(final Book source) {
        return BookModel.builder()
                .title(source.getTitle())
                .author(source.getAuthor())
                .pages(source.getPages())
                .active(true)
                .creationTime(ZonedDateTime.now())
                .updateTime(ZonedDateTime.now())
                .build();
    }

    public static BookModel fromDomainToModelForUpdate(final Book source) {
        return BookModel.builder()
                .id(source.getId())
                .title(source.getTitle())
                .author(source.getAuthor())
                .pages(source.getPages())
                .active(source.isActive())
                .creationTime(source.getCreationTime())
                .updateTime(ZonedDateTime.now())
                .build();
    }

    public static Book fromModelToDomain(final BookModel source) {
        return Book.builder()
                .id(source.getId())
                .title(source.getTitle())
                .author(source.getAuthor())
                .pages(source.getPages())
                .active(source.isActive())
                .creationTime(source.getCreationTime())
                .updateTime(source.getUpdateTime())
                .build();
    }

    public static Book fromVMToDomain(final CreateBookVM source) {
        return Book.builder()
                .title(source.getTitle())
                .author(source.getAuthor())
                .pages(source.getPages())
                .build();
    }

    public static Book fromVMToDomainForUpdate(final UpdateBookVM source) {
        return Book.builder()
                .id(source.getId())
                .title(source.getTitle())
                .author(source.getAuthor())
                .pages(source.getPages())
                .active(source.isActive())
                .build();
    }
}
