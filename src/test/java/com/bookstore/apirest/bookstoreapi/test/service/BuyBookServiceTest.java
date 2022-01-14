package com.bookstore.apirest.bookstoreapi.test.service;

import com.bookstore.apirest.bookstoreapi.dtos.BuyBookDTO;
import com.bookstore.apirest.bookstoreapi.models.BuyBook;
import com.bookstore.apirest.bookstoreapi.models.Client;
import com.bookstore.apirest.bookstoreapi.models.Items;
import com.bookstore.apirest.bookstoreapi.repositories.BuyBookRepository;
import com.bookstore.apirest.bookstoreapi.services.BuyBookService;
import com.bookstore.apirest.bookstoreapi.services.exceptions.ObjectNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.bookstore.apirest.bookstoreapi.test.builder.BuyBookBuilder.createBuyBook;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BuyBookServiceTest {

    @InjectMocks
    private BuyBookService buyBookService;

    @Mock
    private BuyBookRepository buyBookRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Autowired
    private BuyBook buyBook;

    @Autowired
    private BuyBookDTO buyBookDTO;

    @Autowired
    private Optional<BuyBook> optionalBuyBook;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startBuyBook();
    }

    @Test
    void whenFindAllThenReturnAnListOfBuyBooks() {

        when(buyBookRepository.findAll()).thenReturn(Stream.of(createBuyBook().build()).collect(Collectors.toList()));

        List<BuyBook> response = buyBookService.listAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(BuyBook.class, response.get(0).getClass());

        assertEquals(2L, response.get(0).getId());
        assertEquals(20.0, response.get(0).getAmountToPay());
        assertEquals("available", response.get(0).getStatus());
        assertEquals(15, response.get(0).getBuyDay());
    }

    @Test
    void whenfindByIdTheReturnAnBuyBookInstance() {

        when(buyBookRepository.findById(Mockito.anyLong())).thenReturn(optionalBuyBook);

        BuyBook response = buyBookService.findById(2L);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(BuyBook.class, response.getClass());
        Assertions.assertEquals(2L, response.getId());
        assertEquals(20.0, response.getAmountToPay());
        assertEquals("available", response.getStatus());
        assertEquals(15, response.getBuyDay());

    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException(){

        when(buyBookRepository.findById(anyLong())).thenThrow(new ObjectNotFoundException("Object not found"));

        try {
            buyBookService.findById(2L);
        } catch (Exception exception) {
            assertEquals(ObjectNotFoundException.class, exception.getClass());
            assertEquals("Object not found", exception.getMessage());
        }
    }

    @Test
    void whenInsertThenReturnSuccess() {

        BuyBook buyBook = createBuyBook().build();
        buyBookService.insert(buyBook);

        when(buyBookRepository.save(Mockito.any())).thenReturn(buyBook);

        ArgumentCaptor<BuyBook> buyBookArgumentCaptor = ArgumentCaptor.forClass(BuyBook.class);
        verify(buyBookRepository).save(buyBookArgumentCaptor.capture());

        BuyBook response = buyBookArgumentCaptor.getValue();

        assertNotNull(response);
        assertEquals(BuyBook.class, response.getClass());
        assertEquals(2L, response.getId());
        assertEquals(20.0, response.getAmountToPay());
        assertEquals("available", response.getStatus());
        assertEquals(15, response.getBuyDay());


    }

    @Test
    void whenUpdateThenReturnSuccess() {

        BuyBook putBuyBookRequest = createBuyBook()
                .id(2L)
                .amountToPay(20.0)
                .status("available")
                .buyDay(15)
                .build();

        when(buyBookRepository.findById(anyLong())).thenReturn(optionalBuyBook);

        buyBookService.update(BuyBookDTO.from(putBuyBookRequest), 2L);

        ArgumentCaptor<BuyBook> buyBookArgumentCaptor = ArgumentCaptor.forClass(BuyBook.class);
        verify(buyBookRepository).save(buyBookArgumentCaptor.capture());

        BuyBook response = buyBookArgumentCaptor.getValue();

        assertNotNull(response);
        assertEquals(BuyBook.class, response.getClass());
        assertEquals(2L, response.getId());
        assertEquals(20.0, response.getAmountToPay());
        assertEquals("available", response.getStatus());
        assertEquals(15, response.getBuyDay());

    }

    @Test
    void whenUpdateThenReturnAnObjectNotFoundException(){

        when(buyBookRepository.findById(anyLong())).thenThrow(new ObjectNotFoundException("Object not found"));

        try {
            buyBookService.findById(2L);
        } catch (Exception exception) {
            assertEquals(ObjectNotFoundException.class, exception.getClass());
            assertEquals("Object not found", exception.getMessage());
        }
    }

    @Test
    void deleteWithSuccess() {

        when(buyBookRepository.existsById(anyLong())).thenReturn(true);
        buyBookService.delete(2L);
        verify(buyBookRepository).existsById(anyLong());
        verify(buyBookRepository).deleteById(anyLong());
    }

    @Test
    void deleteWithObjectNotFoundException(){

        when(buyBookRepository.findById(anyLong())).thenThrow(new ObjectNotFoundException("Object not found"));
        try{
            buyBookService.delete(2L);
        } catch (Exception exception){
            assertEquals(ObjectNotFoundException.class, exception.getClass());
            assertEquals("Object not found", exception.getMessage());
        }
    }

    public void startBuyBook(){

        List<Items> items = new ArrayList<>();
        items.add(new Items(2L, 5));

        Client clients = new Client(2L, "Test", 22, "(xx) xxxxx-xxxx", "ex@email.com", 'F');

        BuyBook buyBook = new BuyBook(2L, 20.0, 15, "available", items,clients);

        BuyBookDTO buyBookDTO = new BuyBookDTO(2L, 20.0, 15, "available");

        optionalBuyBook = Optional.of(new BuyBook(2L, 20.0, 15, "available", items, clients));
    }
}