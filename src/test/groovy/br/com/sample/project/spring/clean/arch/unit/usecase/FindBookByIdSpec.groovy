package br.com.sample.project.spring.clean.arch.unit.usecase

import br.com.sample.project.spring.clean.arch.domain.Book
import br.com.sample.project.spring.clean.arch.exception.EntityNotFoundException
import br.com.sample.project.spring.clean.arch.external.gateway.BookGateway
import br.com.sample.project.spring.clean.arch.fixtures.BookTemplate
import br.com.sample.project.spring.clean.arch.usecase.FindBookById
import br.com.six2six.fixturefactory.Fixture
import spock.lang.Specification

import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates

class FindBookByIdSpec extends Specification {

    private BookGateway gateway = Mock()
    private FindBookById findBookById = new FindBookById(gateway)

    def setup() {
        loadTemplates("br.com.sample.project.spring.clean.arch.fixtures")
    }

    def "Should execute use case find book by id"() {
        given: "A valid book"
        Book book = Fixture.from(Book).gimme(BookTemplate.VALID_BOOK)

        when: "Execute use case"
        Book result = findBookById.execute(1L)

        then: "Gateway must be called"
        1 * gateway.findById(_ as Long) >> Optional.of(book)

        and: "Result is correct"
        result != null
        result.title == "title test"
    }

    def "Should return EntityNotFoundException when execute use case find by id"() {
        given: "Repository must be called"
        1 * gateway.findById(_ as Long) >> Optional.empty()

        when: "Execute"
        findBookById.execute(1L)

        then: "Result is correct"
        thrown(EntityNotFoundException)
    }

}
