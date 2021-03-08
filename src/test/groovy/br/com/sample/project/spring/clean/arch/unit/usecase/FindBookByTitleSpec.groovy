package br.com.sample.project.spring.clean.arch.unit.usecase

import br.com.sample.project.spring.clean.arch.domain.Book
import br.com.sample.project.spring.clean.arch.exception.EntityNotFoundException
import br.com.sample.project.spring.clean.arch.external.gateway.BookGateway
import br.com.sample.project.spring.clean.arch.fixtures.BookTemplate
import br.com.sample.project.spring.clean.arch.usecase.FindBookByTitle
import br.com.six2six.fixturefactory.Fixture
import spock.lang.Specification

import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates

class FindBookByTitleSpec extends Specification {

    private BookGateway gateway = Mock()
    private FindBookByTitle findBookByTitle = new FindBookByTitle(gateway)

    def setup() {
        loadTemplates("br.com.sample.project.spring.clean.arch.fixtures")
    }

    def "Should execute use case find book by title"() {
        given: "A valid book"
        Book book = Fixture.from(Book).gimme(BookTemplate.VALID_BOOK)

        when: "Execute use case"
        Book result = findBookByTitle.execute("test")

        then: "Gateway must be called"
        1 * gateway.findByTitle(_ as String) >> Optional.of(book)

        and: "Result is correct"
        result != null
        result.title == "title test"
    }

    def "Should return EntityNotFoundException when execute use case find by title"() {
        given: "Repository must be called"
        1 * gateway.findByTitle(_ as String) >> Optional.empty()

        when: "Execute"
        findBookByTitle.execute("test")

        then: "Result is correct"
        thrown(EntityNotFoundException)
    }


}
