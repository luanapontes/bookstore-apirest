package com.bookstore.apirest.bookstoreapi.test.service;

import com.bookstore.apirest.bookstoreapi.dtos.BookDTO;
import com.bookstore.apirest.bookstoreapi.models.Book;
import com.bookstore.apirest.bookstoreapi.models.Category;
import com.bookstore.apirest.bookstoreapi.repositories.BookRepository;
import com.bookstore.apirest.bookstoreapi.services.BookService;
import com.bookstore.apirest.bookstoreapi.services.exceptions.ObjectNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.bookstore.apirest.bookstoreapi.test.builder.BookBuilder.createBook;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Autowired
    private Book book;

    @Autowired
    private BookDTO bookDTO;

    @Autowired
    private Optional<Book> optionalBook;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startBook();
    }

    @Test
    void whenFindAllThenReturnAnListOfBooks() {

        when(bookRepository.findAll()).thenReturn(Stream.of(createBook().build()).collect(Collectors.toList()));

        List<Book> response = bookService.listAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(Book.class, response.get(0).getClass());

        assertEquals(2L, response.get(0).getId());
        Assertions.assertEquals("Test", response.get(0).getName());
        Assertions.assertEquals("Test", response.get(0).getResume());
        Assertions.assertEquals(20, response.get(0).getIsbn());
        Assertions.assertEquals("Luana", response.get(0).getAuthor());
        Assertions.assertEquals(2021, response.get(0).getYearOfPublication());
        Assertions.assertEquals(20.0, response.get(0).getSellPrice());
        Assertions.assertEquals(2, response.get(0).getQuantityAvailable());

    }

    @Test
    void whenfindByIdTheReturnAnBookInstance() {

        when(bookRepository.findById(Mockito.anyLong())).thenReturn(optionalBook);

        Book response = bookService.findById(2L);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Book.class, response.getClass());
        Assertions.assertEquals(2L, response.getId());
        Assertions.assertEquals("Test", response.getName());
        Assertions.assertEquals("Test", response.getResume());
        Assertions.assertEquals(20, response.getIsbn());
        Assertions.assertEquals("Luana", response.getAuthor());
        Assertions.assertEquals(2021, response.getYearOfPublication());
        Assertions.assertEquals(20.0, response.getSellPrice());
        Assertions.assertEquals(2, response.getQuantityAvailable());
    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException(){

        when(bookRepository.findById(anyLong())).thenThrow(new ObjectNotFoundException("Object not found"));

        try {
            bookService.findById(2L);
        } catch (Exception exception) {
            assertEquals(ObjectNotFoundException.class, exception.getClass());
            assertEquals("Object not found", exception.getMessage());
        }
    }

    @Test
    void whenInsertThenReturnSuccess() {

        Book book = createBook().build();
        bookService.insert(book);

        when(bookRepository.save(Mockito.any())).thenReturn(book);

        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(bookArgumentCaptor.capture());

        Book response = bookArgumentCaptor.getValue();

        assertNotNull(response);
        assertEquals(Book.class, response.getClass());
        Assertions.assertEquals(2L, response.getId());
        Assertions.assertEquals("Test", response.getName());
        Assertions.assertEquals("Test", response.getResume());
        Assertions.assertEquals(20, response.getIsbn());
        Assertions.assertEquals("Luana", response.getAuthor());
        Assertions.assertEquals(2021, response.getYearOfPublication());
        Assertions.assertEquals(20.0, response.getSellPrice());
        Assertions.assertEquals(2, response.getQuantityAvailable());

    }

    @Test
    void whenUpdateThenReturnSuccess() {

        Book putBookRequest = createBook()
                .id(2L)
                .name("Test")
                .resume("Test")
                .isbn(20)
                .author("Luana")
                .yearOfPublication(2021)
                .sellPrice(20.0)
                .quantityAvailable(2)
                .build();

        when(bookRepository.findById(anyLong())).thenReturn(optionalBook);

        bookService.update(BookDTO.from(putBookRequest), 2L);

        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(bookArgumentCaptor.capture());

        Book response = bookArgumentCaptor.getValue();

        assertNotNull(response);
        assertEquals(Book.class, response.getClass());
        Assertions.assertEquals(2L, response.getId());
        Assertions.assertEquals("Test", response.getName());
        Assertions.assertEquals("Test", response.getResume());
        Assertions.assertEquals(20, response.getIsbn());
        Assertions.assertEquals("Luana", response.getAuthor());
        Assertions.assertEquals(2021, response.getYearOfPublication());
        Assertions.assertEquals(20.0, response.getSellPrice());
        Assertions.assertEquals(2, response.getQuantityAvailable());

    }

    @Test
    void whenUpdateThenReturnAnObjectNotFoundException(){

        when(bookRepository.findById(anyLong())).thenThrow(new ObjectNotFoundException("Object not found"));

        try {
            bookService.findById(2L);
        } catch (Exception exception) {
            assertEquals(ObjectNotFoundException.class, exception.getClass());
            assertEquals("Object not found", exception.getMessage());
        }
    }

    @Test
    void deleteWithSuccess() {

        when(bookRepository.existsById(anyLong())).thenReturn(true);
        bookService.delete(2L);
        verify(bookRepository).existsById(anyLong());
        verify(bookRepository).deleteById(anyLong());
    }

    @Test
    void deleteWithObjectNotFoundException(){

        when(bookRepository.findById(anyLong())).thenThrow(new ObjectNotFoundException("Object not found"));
        try{
            bookService.delete(2L);
        } catch (Exception exception){
            assertEquals(ObjectNotFoundException.class, exception.getClass());
            assertEquals("Object not found", exception.getMessage());
        }
    }

    private void startBook(){
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(2L, "Mangá"));

        Book book = new Book(2L,"Test", "Test", 20, "Luana", 2021, 20.0, 2, categories);

        BookDTO bookDTO = new BookDTO(2L,"Test", "Test", 20, "Luana", 2021, 20.0, 2, categories);

        optionalBook = Optional.of(new Book(2L,"Test", "Test", 20, "Luana", 2021, 20.0, 2, categories));


    }
}