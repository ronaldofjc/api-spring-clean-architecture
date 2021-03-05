package br.com.sample.project.spring.clean.arch.unit.usecase

import br.com.sample.project.spring.clean.arch.domain.Book
import br.com.sample.project.spring.clean.arch.exception.BusinessException
import br.com.sample.project.spring.clean.arch.external.gateway.BookGateway
import br.com.sample.project.spring.clean.arch.fixtures.BookTemplate
import br.com.sample.project.spring.clean.arch.usecase.CreateBook
import br.com.six2six.fixturefactory.Fixture
import spock.lang.Specification
import spock.lang.Unroll

import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates

class CreateBookSpec extends Specification {

    BookGateway gateway = Mock()
    CreateBook createBook = new CreateBook(gateway)

    private title = "teste"

    def setup() {
        loadTemplates("br.com.sample.project.spring.clean.arch.fixtures")
    }

    @Unroll
    def "should create new book when #scenario"() {
        given: "An empty Exception"
        Exception ex

        and: "Valid books"
        Book book = Fixture.from(Book).gimme(BookTemplate.VALID_BOOK)
        Book book2 = Fixture.from(Book).gimme(BookTemplate.VALID_BOOK)
        book2.title = title

        when: "Execute create book on gateway"
        try {
            createBook.execute(book)
        } catch (Exception e) {
            ex = e
        }

        then: "Result is correct"
        if (exception == null) {
            assert ex == null
        } else {
            assert ex.class == exception
        }

        and: "Gateway findByTitle must be called"
        1 * gateway.findByTitle(_ as String) >> {
            if (validBook) {
                return Optional.of(book)
            } else {
                return Optional.empty()
            }
        }

        and: "Gateway create must be called"
        callGatewayCreate * gateway.create(_ as Book) >> book2

        and: "Validate title"
        book2.title == title

        where:
        scenario                    | exception         | validBook | callGatewayCreate
        "book title not exists"     | null              | false     | 1
        "book title already exists" | BusinessException | true      | 0
    }

}
