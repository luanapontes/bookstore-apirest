package com.bookstore.apirest.bookstoreapi.test.controller;

import com.bookstore.apirest.bookstoreapi.controllers.BookController;
import com.bookstore.apirest.bookstoreapi.dtos.BookDTO;
import com.bookstore.apirest.bookstoreapi.models.Book;
import com.bookstore.apirest.bookstoreapi.models.Category;
import com.bookstore.apirest.bookstoreapi.repositories.BookRepository;
import com.bookstore.apirest.bookstoreapi.services.BookService;
import com.bookstore.apirest.bookstoreapi.services.CategoryService;
import com.bookstore.apirest.bookstoreapi.services.exceptions.ObjectNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.bookstore.apirest.bookstoreapi.test.builder.BookBuilder.createBook;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.ArrayList;
import java.util.List;


@WebMvcTest(BookController.class)
@ExtendWith(SpringExtension.class)
public class BookControllerTest {

    private final String URL_BOOK = "/books";

    @InjectMocks
    private BookController bookController;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private  BookService bookService;

    @MockBean
    private CategoryService categoryService;

    @BeforeEach
    public void setup() throws  Exception{
    objectMapper = new ObjectMapper();
        MockitoAnnotations.openMocks(this);
        startBook();
    }

    @Test
    void whenFindByIdThenReturnSuccess() throws Exception{

        when(bookService.findById(anyLong())).thenReturn(createBook().build());

        mockMvc.perform(get(URL_BOOK + "/{id}", 2L).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("Test")))
                .andExpect(jsonPath("$.isbn", is(20)))
                .andExpect(jsonPath("$.author", is("Luana")))
                .andExpect(jsonPath("$.resume", is("Test")))
                .andExpect(jsonPath("$.quantityAvailable", is(2)))
                .andExpect(jsonPath("$.yearOfPublication", is(2021)))
                .andExpect(jsonPath("$.sellPrice", is(20.0)));

        verify(bookService).findById(anyLong());

    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException() throws Exception{

        when(bookService.findById(anyLong())).thenThrow(new ObjectNotFoundException("Object not found"));

        mockMvc.perform(get(URL_BOOK + "/{id}", 2L).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(bookService).findById(2L);
    }


    @Test
    void whenFindAllThenReturnAnListOfBooks() throws Exception{
        when(bookService.listAll()).thenReturn(Lists.newArrayList(
                createBook().id(2L).build()));

        mockMvc.perform(get(URL_BOOK).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].name", is("Test")))
                .andExpect(jsonPath("$[0].resume", is("Test")))
                .andExpect(jsonPath("$[0].isbn", is(20)))
                .andExpect(jsonPath("$[0].author", is("Luana")))
                .andExpect(jsonPath("$[0].yearOfPublication", is(2021)))
                .andExpect(jsonPath("$[0].sellPrice", is(20.0)))
                .andExpect(jsonPath("$[0].quantityAvailable", is(2)));

        verify(bookService).listAll();
    }

    @Test
    void whenInsertThenReturnSuccess() throws Exception {

        Book book = createBook()
                .id(2L)
                .name("Test")
                .resume("Test")
                .isbn(20)
                .author("Luana")
                .yearOfPublication(2021)
                .sellPrice(20.0)
                .quantityAvailable(2)
                .build();

        when(bookService.insert(any())).thenReturn(book);

        mockMvc.perform(post(URL_BOOK)
                        .content(objectMapper.writeValueAsString(book))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(bookService).insert(any(Book.class));
    }

    @Test
    void whenUpdateThenReturnSuccess() throws Exception{

        Book book = createBook()
                .id(2L)
                .name("Test")
                .resume("Test")
                .isbn(20)
                .author("Luana")
                .yearOfPublication(2021)
                .sellPrice(20.0)
                .quantityAvailable(2)
                .build();

        when(bookService.findById(2L)).thenReturn(book);
        when(bookService.insert(any(Book.class))).thenReturn(book);

        mockMvc.perform(put(URL_BOOK + "/{id}", 2L)
                        .content(objectMapper.writeValueAsString(book))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(bookService).update(any(BookDTO.class), eq(2L));
    }
    @Test
    void deleteWithSuccess() throws Exception{

        mockMvc.perform(delete(URL_BOOK + "/{id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(bookService).delete(anyLong());
    }

        private void startBook(){
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(2L, "Mangá"));

        Book book = new Book(2L,"Test", "Test", 20, "Luana", 2021, 20.0, 2, categories);

        BookDTO bookDTO = new BookDTO(2L,"Test", "Test", 20, "Luana", 2021, 20.0, 2, categories);

    }

}
