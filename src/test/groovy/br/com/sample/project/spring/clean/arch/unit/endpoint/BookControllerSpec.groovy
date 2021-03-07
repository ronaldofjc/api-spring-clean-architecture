package br.com.sample.project.spring.clean.arch.unit.endpoint

import br.com.sample.project.spring.clean.arch.domain.Book
import br.com.sample.project.spring.clean.arch.endpoint.BookController
import br.com.sample.project.spring.clean.arch.endpoint.entity.CreateBookVM
import br.com.sample.project.spring.clean.arch.endpoint.entity.UpdateBookVM
import br.com.sample.project.spring.clean.arch.fixtures.BookTemplate
import br.com.sample.project.spring.clean.arch.fixtures.CreateBookVMTemplate
import br.com.sample.project.spring.clean.arch.fixtures.UpdateBookVMTemplate
import br.com.sample.project.spring.clean.arch.usecase.*
import br.com.six2six.fixturefactory.Fixture
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*

@WebMvcTest(value = BookController)
class BookControllerSpec extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private CreateBook createBook

    @Autowired
    private FindAllBooks findAllBooks

    @Autowired
    private UpdateBook updateBook

    @Autowired
    private RemoveBook removeBook

    @Autowired
    private FindBookById findBookById

    @Autowired
    private FindBookByTitle findBookByTitle

    @Autowired
    private CountBooks countBooks

    Book book

    def setup() {
        loadTemplates("br.com.sample.project.spring.clean.arch.fixtures")

        book = Fixture.from(Book).gimme(BookTemplate.VALID_BOOK)
    }

    ObjectMapper mapper = new ObjectMapper()

    def "Should validate endpoint create book"() {
        given: "An create book vm"
        CreateBookVM createBookVM = Fixture.from(CreateBookVM).gimme(CreateBookVMTemplate.VALID_BOOK_VM)

        when: "Call endpoint"
        MvcResult result = mvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createBookVM))).andReturn()

        then: "Result is correct"
        1 * createBook.execute(_ as Book)

        and: "Http status should be CREATED"
        result.getResponse().getStatus() == HttpStatus.CREATED.value()
    }

    def "Should validate endpoint find all books"() {
        when: "Call endpoint"
        MvcResult result = mvc.perform(get("/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()

        then: "Result is correct"
        1 * findAllBooks.execute() >> List.of(book)

        and: "Http status should be OK"
        result.getResponse().getStatus() == HttpStatus.OK.value()
    }

    def "Should validate endpoint update book"() {
        given: "Valid update book vm"
        UpdateBookVM updateBookVM = Fixture.from(UpdateBookVM).gimme(UpdateBookVMTemplate.VALID_UPDATE_BOOK_VM)

        when: "Call endpoint"
        MvcResult result = mvc.perform(put("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updateBookVM))).andReturn()

        then: "Result is correct"
        1 * updateBook.execute(_ as Book) >> book

        and: "Http status should be OK"
        result.getResponse().getStatus() == HttpStatus.OK.value()

        and: "Validate content result"
        Map<String, Object> content = mapper.readValue(result.getResponse().getContentAsString(), Map)
        content.get("title") == "title test"
    }

    def "Should validate endpoint remove book"() {
        when: "Call endpoint"
        MvcResult result = mvc.perform(delete("/books/" + 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()

        then: "Result is correct"
        1 * removeBook.execute(_ as Long)

        and: "Http status should be No Content"
        result.getResponse().getStatus() == HttpStatus.NO_CONTENT.value()
    }

    def "Should validate endpoint find book by id"() {
        when: "Call endpoint"
        MvcResult result = mvc.perform(get("/books/" + 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()

        then: "Result is correct"
        1 * findBookById.execute(_ as Long) >> book

        and: "Http status should be OK"
        result.getResponse().getStatus() == HttpStatus.OK.value()

        and: "Validate content result"
        Map<String, Object> content = mapper.readValue(result.getResponse().getContentAsString(), Map)
        content.get("title") == "title test"
    }

    def "Should validate endpoint find book by title"() {
        when: "Call endpoint"
        MvcResult result = mvc.perform(get("/books/title/test")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()

        then: "Result is correct"
        1 * findBookByTitle.execute(_ as String) >> book

        and: "Http status should be OK"
        result.getResponse().getStatus() == HttpStatus.OK.value()

        and: "Validate content result"
        Map<String, Object> content = mapper.readValue(result.getResponse().getContentAsString(), Map)
        content.get("title") == "title test"
    }

    def "Should validate endpoint count all books"() {
        when: "Call endpoint"
        MvcResult result = mvc.perform(get("/books/count")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()

        then: "Result is correct"
        1 * countBooks.execute() >> 100

        and: "Http status should be OK"
        result.getResponse().getStatus() == HttpStatus.OK.value()
    }

    @TestConfiguration
    static class Mocks {
        def factory = new DetachedMockFactory()

        @Bean
        CreateBook createBook() {
            factory.Mock(CreateBook)
        }

        @Bean
        FindAllBooks findAllBooks() {
            factory.Mock(FindAllBooks)
        }

        @Bean
        UpdateBook updateBook() {
            factory.Mock(UpdateBook)
        }

        @Bean
        RemoveBook removeBook() {
            factory.Mock(RemoveBook)
        }

        @Bean
        FindBookById findBookById() {
            factory.Mock(FindBookById)
        }

        @Bean
        FindBookByTitle findBookByTitle() {
            factory.Mock(FindBookByTitle)
        }

        @Bean
        CountBooks countBooks() {
            factory.Mock(CountBooks)
        }

    }

}
