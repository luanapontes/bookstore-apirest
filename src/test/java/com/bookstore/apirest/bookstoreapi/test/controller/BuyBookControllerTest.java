package com.bookstore.apirest.bookstoreapi.test.controller;

import com.bookstore.apirest.bookstoreapi.controllers.BuyBookController;
import com.bookstore.apirest.bookstoreapi.dtos.BuyBookDTO;
import com.bookstore.apirest.bookstoreapi.models.BuyBook;
import com.bookstore.apirest.bookstoreapi.models.Client;
import com.bookstore.apirest.bookstoreapi.models.Items;
import com.bookstore.apirest.bookstoreapi.repositories.BuyBookRepository;
import com.bookstore.apirest.bookstoreapi.services.BuyBookService;
import com.bookstore.apirest.bookstoreapi.services.exceptions.ObjectNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.bookstore.apirest.bookstoreapi.test.builder.BuyBookBuilder.createBuyBook;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BuyBookController.class)
@ExtendWith(SpringExtension.class)
class BuyBookControllerTest {

    private final String URL_BUYBOOK = "/buybooks";

    @InjectMocks
    private BuyBookController buyBookController;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BuyBookRepository buyBookRepository;

    @MockBean
    private BuyBookService buyBookService;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        MockitoAnnotations.openMocks(this);
        startBuyBook();
    }

    @Test
    void whenFindByIdThenReturnSuccess() throws Exception{

        when(buyBookService.findById(anyLong())).thenReturn(createBuyBook().build());

        mockMvc.perform(get(URL_BUYBOOK + "/{id}", 2L).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.amountToPay", is(20.0)))
                .andExpect(jsonPath("$.status", is("available")))
                .andExpect(jsonPath("$.buyDay", is(15)));

        verify(buyBookService).findById(anyLong());

    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException() throws Exception{

        when(buyBookService.findById(anyLong())).thenThrow(new ObjectNotFoundException("Object not found"));

        mockMvc.perform(get(URL_BUYBOOK + "/{id}", 2L).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(buyBookService).findById(2L);
    }


    @Test
    void whenFindAllThenReturnAnListOfBooks() throws Exception{
        when(buyBookService.listAll()).thenReturn(Lists.newArrayList(
                createBuyBook().id(2L).build()));

        mockMvc.perform(get(URL_BUYBOOK).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].amountToPay", is(20.0)))
                .andExpect(jsonPath("$[0].status", is("available")))
                .andExpect(jsonPath("$[0].buyDay", is(15)));

        verify(buyBookService).listAll();
    }

    @Test
    void whenInsertThenReturnSuccess() throws Exception {

        BuyBook buyBook = createBuyBook()
                .id(2L)
                .amountToPay(20.0)
                .status("available")
                .buyDay(15)
                .build();

        when(buyBookService.insert(any())).thenReturn(buyBook);

        mockMvc.perform(post(URL_BUYBOOK)
                        .content(objectMapper.writeValueAsString(buyBook))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(buyBookService).insert(any(BuyBook.class));
    }

    @Test
    void whenUpdateThenReturnSuccess() throws Exception{

        BuyBook buyBook = createBuyBook()
                .id(2L)
                .amountToPay(20.0)
                .status("available")
                .buyDay(15)
                .build();

        when(buyBookService.findById(2L)).thenReturn(buyBook);
        when(buyBookService.insert(any(BuyBook.class))).thenReturn(buyBook);

        mockMvc.perform(put(URL_BUYBOOK + "/{id}", 2L)
                        .content(objectMapper.writeValueAsString(buyBook))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(buyBookService).update(any(BuyBookDTO.class), eq(2L));
    }
    @Test
    void deleteWithSuccess() throws Exception{

        mockMvc.perform(delete(URL_BUYBOOK + "/{id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(buyBookService).delete(anyLong());
    }

    private void startBuyBook(){

        List<Items> items = new ArrayList<>();
        items.add(new Items(2L, 5));

        Client clients = new Client(2L, "Test", 22, "(xx) xxxxx-xxxx", "ex@email.com", 'F');

        BuyBook buyBook = new BuyBook(2L, 20.0, 15, "available", items, clients);

        BuyBookDTO buyBookDTO = new BuyBookDTO(2L, 20.0, 15, "available");

    }
}