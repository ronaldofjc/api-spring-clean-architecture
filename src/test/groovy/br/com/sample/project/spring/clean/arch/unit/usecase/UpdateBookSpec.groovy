package br.com.sample.project.spring.clean.arch.unit.usecase

import br.com.sample.project.spring.clean.arch.domain.Book
import br.com.sample.project.spring.clean.arch.exception.BusinessException
import br.com.sample.project.spring.clean.arch.external.gateway.BookGateway
import br.com.sample.project.spring.clean.arch.fixtures.BookTemplate
import br.com.sample.project.spring.clean.arch.usecase.FindBookById
import br.com.sample.project.spring.clean.arch.usecase.UpdateBook
import br.com.six2six.fixturefactory.Fixture
import spock.lang.Specification
import spock.lang.Unroll

import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates

class UpdateBookSpec extends Specification {

    private BookGateway gateway = Mock()
    private FindBookById findBookById = Mock()
    private UpdateBook updateBook = new UpdateBook(gateway, findBookById)

    def setup() {
        loadTemplates("br.com.sample.project.spring.clean.arch.fixtures")
    }

    @Unroll
    def "Should execute use case update book when #scenario"() {
        given: "Valid books"
        Book book = Fixture.from(Book).gimme(BookTemplate.VALID_BOOK)
        Book bookWithId = Fixture.from(Book).gimme(BookTemplate.VALID_BOOK_WITH_ID)

        and: "An empty Exception"
        Exception ex

        and: "Validate title"
        if (!titleDifferent) {
            bookWithId.setTitle(book.title)
        }

        when: "Execute use case update book"
        try {
            updateBook.execute(bookWithId)
        } catch (Exception e) {
            ex = e
        }

        then: "Result is correct"
        if (exception == null) {
            assert ex == null
        } else {
            assert ex.class == exception
        }

        and: "Use case find book by id must be called"
        1 * findBookById.execute(_ as Long) >> book

        and: "Gateway find book by title must be called"
        callFindByTitle * gateway.findByTitle(_ as String) >> {
            if (bookOptionalExists) {

                return Optional.of(book)
            } else {
                return Optional.empty()
            }
        }

        and: "Gateway update book must be called"
        callUpdateBook * gateway.update(_ as Book) >> book

        where:
        scenario                                        | titleDifferent | exception         | bookOptionalExists | callFindByTitle | callUpdateBook
        "update book with title is equal on database"   | false          | null              | false              | 0               | 1
        "already exists book with title on database"    | true           | BusinessException | true               | 1               | 0
        "update book with title not exists on database" | true           | null              | false              | 1               | 1
    }

}
