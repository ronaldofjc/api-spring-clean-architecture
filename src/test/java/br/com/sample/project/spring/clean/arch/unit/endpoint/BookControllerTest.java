package br.com.sample.project.spring.clean.arch.unit.endpoint;

import br.com.sample.project.spring.clean.arch.domain.Book;
import br.com.sample.project.spring.clean.arch.endpoint.BookController;
import br.com.sample.project.spring.clean.arch.fixtures.BookFixture;
import br.com.sample.project.spring.clean.arch.usecase.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(BookController.class)
class BookControllerTest {

    private static final String ENDPOINT = "/books";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CreateBook createBook;

    @MockBean
    private FindAllBooks findAllBooks;

    @MockBean
    private UpdateBook updateBook;

    @MockBean
    private RemoveBook removeBook;

    @MockBean
    private FindBookById findBookById;

    @MockBean
    private FindBookByTitle findBookByTitle;

    @MockBean
    private CountBooks countBooks;

    Book book = BookFixture.gimmeBasicBook();

    @Test
    void shouldValidateApiCreateBook() throws Exception {
        // given
        final String request = new String(Files.readAllBytes(Paths.get("src/test/resources/payloads/createBookVM.json")));
        // when
        this.mvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(request))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        // then
        verify(createBook, times(1)).execute(any());
    }

    @Test
    void shouldValidateApiFindAllBooks() throws Exception {
        // when
        when(findAllBooks.execute()).thenReturn(List.of(book));
        // and execute
        this.mvc.perform(get(ENDPOINT))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(containsString("Title test")));
        // then
        verify(findAllBooks, times(1)).execute();
    }

    @Test
    void shouldValidateApiUpdateBook() throws Exception {
        // given
        final String request = new String(Files.readAllBytes(Paths.get("src/test/resources/payloads/updateBookVM.json")));
        // when
        when(updateBook.execute(any())).thenReturn(book);
        // and execute
        this.mvc.perform(put(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(request))
                .andExpect(MockMvcResultMatchers.status().isOk());
        // then
        verify(updateBook, times(1)).execute(any());
    }

    @Test
    void shouldValidateApiRemoveBook() throws Exception {
        // when
        this.mvc.perform(delete(ENDPOINT + "/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        // then
        verify(removeBook, times(1)).execute(anyLong());
    }

    @Test
    void shouldValidateApiFindBookById() throws Exception {
        // when
        when(findBookById.execute(any())).thenReturn(book);
        // and execute
        this.mvc.perform(get(ENDPOINT + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(containsString("Title test")));
        // then
        verify(findBookById, times(1)).execute(anyLong());
    }

    @Test
    void shouldValidateApiFindBookByTitle() throws Exception {
        // when
        when(findBookByTitle.execute(any())).thenReturn(book);
        // and execute
        this.mvc.perform(get(ENDPOINT + "/title/test"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(containsString("Title test")));
        // then
        verify(findBookByTitle, times(1)).execute(anyString());
    }

    @Test
    void shouldValidateApiCountBooks() throws Exception {
        // when
        when(countBooks.execute()).thenReturn(1L);
        // and execute
        this.mvc.perform(get(ENDPOINT + "/count"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        // then
        verify(countBooks, times(1)).execute();
    }

}
