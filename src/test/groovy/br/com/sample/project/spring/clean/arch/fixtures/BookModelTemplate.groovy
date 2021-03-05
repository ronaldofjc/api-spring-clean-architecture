package br.com.sample.project.spring.clean.arch.fixtures

import br.com.sample.project.spring.clean.arch.external.database.repository.model.BookModel
import br.com.six2six.fixturefactory.Fixture
import br.com.six2six.fixturefactory.Rule
import br.com.six2six.fixturefactory.loader.TemplateLoader

import java.time.ZonedDateTime

import static br.com.sample.project.spring.clean.arch.external.database.repository.model.BookModel.Fields.*

class BookModelTemplate implements TemplateLoader {

    public static final String VALID_BOOK_MODEL = "Valid book model"

    @Override
    void load() {
        Fixture.of(BookModel).addTemplate(VALID_BOOK_MODEL, new Rule() {
            {
                add(id, 1L)
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
