package br.com.sample.project.spring.clean.arch.unit.usecase

import br.com.sample.project.spring.clean.arch.domain.Book
import br.com.sample.project.spring.clean.arch.external.gateway.BookGateway
import br.com.sample.project.spring.clean.arch.fixtures.BookTemplate
import br.com.sample.project.spring.clean.arch.usecase.FindAllBooks
import br.com.six2six.fixturefactory.Fixture
import spock.lang.Specification

import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates

class FindAllBooksSpec extends Specification {

    private BookGateway gateway = Mock()
    private FindAllBooks findAllBooks = new FindAllBooks(gateway)

    def setup() {
        loadTemplates("br.com.sample.project.spring.clean.arch.fixtures")
    }

    def "Should return find all books"() {
        given: "A valid book"
        Book book = Fixture.from(Book).gimme(BookTemplate.VALID_BOOK)

        when: "Execute use case"
        List<Book> result = findAllBooks.execute()

        then: "Gateway must be called"
        1 * gateway.findAll() >> List.of(book)

        and: "Result is correct"
        !result.isEmpty()
        result.get(0).title == "title test"
    }

}
