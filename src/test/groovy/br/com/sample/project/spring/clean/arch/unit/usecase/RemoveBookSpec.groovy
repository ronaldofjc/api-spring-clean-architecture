package br.com.sample.project.spring.clean.arch.unit.usecase

import br.com.sample.project.spring.clean.arch.domain.Book
import br.com.sample.project.spring.clean.arch.external.gateway.BookGateway
import br.com.sample.project.spring.clean.arch.fixtures.BookTemplate
import br.com.sample.project.spring.clean.arch.usecase.FindBookById
import br.com.sample.project.spring.clean.arch.usecase.RemoveBook
import br.com.six2six.fixturefactory.Fixture
import spock.lang.Specification

import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates

class RemoveBookSpec extends Specification {

    private BookGateway gateway = Mock()
    private FindBookById findBookById = Mock()
    private RemoveBook removeBook = new RemoveBook(gateway, findBookById)

    def setup() {
        loadTemplates("br.com.sample.project.spring.clean.arch.fixtures")
    }

    def "Should execute use case remove book"() {
        given: "A valid book"
        Book book = Fixture.from(Book).gimme(BookTemplate.VALID_BOOK)

        when: "Execute use case"
        removeBook.execute(1L)

        then: "Result is correct"
        1 * findBookById.execute(_ as Long) >> book
        1 * gateway.update(_ as Book)
    }
}
