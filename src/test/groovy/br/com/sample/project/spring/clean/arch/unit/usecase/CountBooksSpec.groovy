package br.com.sample.project.spring.clean.arch.unit.usecase

import br.com.sample.project.spring.clean.arch.external.gateway.BookGateway
import br.com.sample.project.spring.clean.arch.usecase.CountBooks
import spock.lang.Specification

import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates

class CountBooksSpec extends Specification {

    private BookGateway gateway = Mock()
    private CountBooks countBooks = new CountBooks(gateway)

    def setup() {
        loadTemplates("br.com.sample.project.spring.clean.arch.fixtures")
    }

    def "Should return count all books"() {
        given: "A valid long value"
        def one = 1L

        when: "Execute use case"
        def result = countBooks.execute()

        then: "Result is correct"
        1 * gateway.count() >> one

        and: "Validate result"
        result == one
    }

}
