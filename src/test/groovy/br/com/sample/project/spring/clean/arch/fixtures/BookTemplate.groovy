package br.com.sample.project.spring.clean.arch.fixtures

import br.com.sample.project.spring.clean.arch.domain.Book
import br.com.six2six.fixturefactory.Fixture
import br.com.six2six.fixturefactory.Rule
import br.com.six2six.fixturefactory.loader.TemplateLoader

import java.time.ZonedDateTime

import static br.com.sample.project.spring.clean.arch.external.database.repository.model.BookModel.Fields.*

class BookTemplate implements TemplateLoader {

    public static final String VALID_BOOK = "Valid book"

    @Override
    void load() {
        Fixture.of(Book).addTemplate(VALID_BOOK, new Rule() {
            {
                add(title, "title test")
                add(author, "author test")
                add(pages, 100)
                add(active, true)
                add(creationTime, ZonedDateTime.now())
                add(updateTime, ZonedDateTime.now())
            }
        })
    }
}
