package br.com.sample.project.spring.clean.arch.unit.adapter

import br.com.sample.project.spring.clean.arch.adapter.BookAdapter
import br.com.sample.project.spring.clean.arch.domain.Book
import br.com.sample.project.spring.clean.arch.endpoint.entity.CreateBookVM
import br.com.sample.project.spring.clean.arch.endpoint.entity.UpdateBookVM
import br.com.sample.project.spring.clean.arch.external.database.repository.model.BookModel
import br.com.six2six.fixturefactory.Fixture
import spock.lang.Specification

import static br.com.sample.project.spring.clean.arch.fixtures.BookModelTemplate.VALID_BOOK_MODEL
import static br.com.sample.project.spring.clean.arch.fixtures.BookTemplate.VALID_BOOK
import static br.com.sample.project.spring.clean.arch.fixtures.CreateBookVMTemplate.VALID_BOOK_VM
import static br.com.sample.project.spring.clean.arch.fixtures.UpdateBookVMTemplate.VALID_UPDATE_BOOK_VM
import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates

class BookAdapterSpec extends Specification {

    CreateBookVM createBookVM
    UpdateBookVM updateBookVM
    Book book
    BookModel bookModel

    def setup() {
        loadTemplates("br.com.sample.project.spring.clean.arch.fixtures")

        createBookVM = Fixture.from(CreateBookVM).gimme(VALID_BOOK_VM)
        book = Fixture.from(Book).gimme(VALID_BOOK)
        bookModel = Fixture.from(BookModel).gimme(VALID_BOOK_MODEL)
        updateBookVM = Fixture.from(UpdateBookVM).gimme(VALID_UPDATE_BOOK_VM)
    }

    def "Convert Book to BookModel for create"() {
        when: "Convert entity"
        BookModel result = BookAdapter.fromDomainToModel(book)

        then: "Result is correct"
        result.title == book.title
        result.author == book.author
        result.pages == book.pages
        result.active == book.active
    }

    def "Convert Book to BookModel for update"() {
        when: "Convert entity"
        BookModel result = BookAdapter.fromDomainToModelForUpdate(book)

        then: "Result is correct"
        result.id == book.id
        result.title == book.title
        result.author == book.author
        result.pages == book.pages
        result.active == book.active
        result.creationTime == book.creationTime
    }

    def "Convert BookModel to Book"() {
        when: "Convert entity"
        Book result = BookAdapter.fromModelToDomain(bookModel)

        then: "Result is correct"
        result.id == bookModel.id
        result.title == bookModel.title
        result.author == bookModel.author
        result.pages == bookModel.pages
        result.active == bookModel.active
        result.creationTime == bookModel.creationTime
        result.updateTime == bookModel.updateTime
    }

    def "Convert CreateBookVM to Book"() {
        when: "Convert entity"
        Book result = BookAdapter.fromVMToDomain(createBookVM)

        then: "Result is correct"
        result.title == createBookVM.title
        result.author == createBookVM.author
        result.pages == createBookVM.pages
    }

    def "Convert UpdateBookVM to Book"() {
        when: "Convert entity"
        Book result = BookAdapter.fromVMToDomainForUpdate(updateBookVM)

        then: "Result is correct"
        result.id == updateBookVM.id
        result.title == updateBookVM.title
        result.author == updateBookVM.author
        result.pages == updateBookVM.pages
        result.active == updateBookVM.active
    }

    def "Validate illegalStateException on Adapter"() throws NoSuchMethodException {
        given: "Create Constructor"
        def constructor = BookAdapter.class.getDeclaredConstructor()
        constructor.setAccessible(true)

        when: "Execute"
        try {
            constructor.newInstance()
        } catch (final Exception e) {
            throw new IllegalStateException(e)
        }

        then: "Result is correct"
        thrown(IllegalStateException)
    }
}
