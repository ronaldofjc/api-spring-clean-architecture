package br.com.sample.project.spring.clean.arch.unit.external.gateway.impl

import br.com.sample.project.spring.clean.arch.domain.Book
import br.com.sample.project.spring.clean.arch.exception.GatewayException
import br.com.sample.project.spring.clean.arch.external.database.repository.BookRepository
import br.com.sample.project.spring.clean.arch.external.database.repository.model.BookModel
import br.com.sample.project.spring.clean.arch.external.gateway.impl.BookGatewayImpl
import br.com.sample.project.spring.clean.arch.fixtures.BookModelTemplate
import br.com.sample.project.spring.clean.arch.fixtures.BookTemplate
import br.com.six2six.fixturefactory.Fixture
import spock.lang.Specification
import spock.lang.Unroll

import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates

class BookGatewayImplSpec extends Specification {

    private BookRepository bookRepository = Mock()
    private BookGatewayImpl bookGateway = new BookGatewayImpl(bookRepository)

    BookModel bookModel
    Book book

    def setup() {
        loadTemplates("br.com.sample.project.spring.clean.arch.fixtures")

        bookModel = Fixture.from(BookModel).gimme(BookModelTemplate.VALID_BOOK_MODEL)
        book = Fixture.from(Book).gimme(BookTemplate.VALID_BOOK)
    }

    def "Should execute gateway find by id with success"() {
        when: "Execute"
        Optional<BookModel> result = bookGateway.findById(1)

        then: "Result is correct"
        result.isPresent()

        and: "Repository must be called"
        1 * bookRepository.findById(_ as Long) >> Optional.of(bookModel)
    }

    def "Should return GatewayException when execute gateway find by id"() {
        given: "Repository must be called"
        1 * bookRepository.findById(_ as Long) >> { throw new GatewayException() }

        when: "Execute"
        Optional<BookModel> result = bookGateway.findById(1)

        then: "Result is correct"
        thrown(GatewayException)
    }

    @Unroll
    def "should execute create new book when #scenario"() {
        given: "An empty Exception"
        Exception ex

        when: "Execute create book on gateway"
        try {
            bookGateway.create(book)
        } catch (Exception e) {
            ex = e
        }

        then: "Result is correct"
        if (exception == null) {
            assert ex == null
        } else {
            assert ex.class == exception
        }

        and: "Repository must be called"
        callRepository * bookRepository.save(_ as BookModel) >> {
            if (validRepository) {
                return bookModel
            } else {
                throw new GatewayException()
            }
        }

        where:
        scenario                     | exception        | validRepository | callRepository
        "success"                    | null             | true            | 1
        "error occurs on repository" | GatewayException | false           | 1
    }

}
