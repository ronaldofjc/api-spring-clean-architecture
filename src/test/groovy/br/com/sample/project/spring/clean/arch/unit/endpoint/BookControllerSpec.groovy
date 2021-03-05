package br.com.sample.project.spring.clean.arch.unit.endpoint

import br.com.sample.project.spring.clean.arch.domain.Book
import br.com.sample.project.spring.clean.arch.endpoint.BookController
import br.com.sample.project.spring.clean.arch.endpoint.entity.CreateBookVM
import br.com.sample.project.spring.clean.arch.fixtures.CreateBookVMTemplate
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post


@WebMvcTest(value = BookController)
class BookControllerSpec extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private CreateBook createBook

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

    def setup() {
        loadTemplates("br.com.sample.project.spring.clean.arch.fixtures")
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


}
