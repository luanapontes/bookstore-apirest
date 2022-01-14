package com.bookstore.apirest.bookstoreapi.test.controller;

import com.bookstore.apirest.bookstoreapi.controllers.ClientController;
import com.bookstore.apirest.bookstoreapi.dtos.ClientDTO;
import com.bookstore.apirest.bookstoreapi.models.Client;
import com.bookstore.apirest.bookstoreapi.repositories.ClientRepository;
import com.bookstore.apirest.bookstoreapi.services.ClientService;
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

import static com.bookstore.apirest.bookstoreapi.test.builder.ClientBuilder.createClient;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
@ExtendWith(SpringExtension.class)
class ClientControllerTest {

    private final String URL_CLIENT = "/clients";

    @InjectMocks
    ClientController clientController;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientRepository clientRepository;

    @MockBean
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        MockitoAnnotations.openMocks(this);
        startClient();
    }

    @Test
    void whenFindByIdThenReturnSuccess() throws Exception{

        when(clientService.findById(anyLong())).thenReturn(createClient().build());

        mockMvc.perform(get(URL_CLIENT + "/{id}", 2L).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("Test")))
                .andExpect(jsonPath("$.age", is(22)))
                .andExpect(jsonPath("$.contact", is("(xx) xxxxx-xxxx")))
                .andExpect(jsonPath("$.email", is("ex@email.com")))
                .andExpect(jsonPath("$.gender", is("F")));

        verify(clientService).findById(anyLong());

    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException() throws Exception{

        when(clientService.findById(anyLong())).thenThrow(new ObjectNotFoundException("Object not found"));

        mockMvc.perform(get(URL_CLIENT + "/{id}", 2L).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(clientService).findById(2L);
    }


    @Test
    void whenFindAllThenReturnAnListOfClients() throws Exception{
        when(clientService.listAll()).thenReturn(Lists.newArrayList(
                createClient().id(2L).build()));

        mockMvc.perform(get(URL_CLIENT).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].name", is("Test")))
                .andExpect(jsonPath("$[0].age", is(22)))
                .andExpect(jsonPath("$[0].contact", is("(xx) xxxxx-xxxx")))
                .andExpect(jsonPath("$[0].email", is("ex@email.com")))
                .andExpect(jsonPath("$[0].gender", is("F")));

        verify(clientService).listAll();
    }

    @Test
    void whenInsertThenReturnSuccess() throws Exception {

        Client client = createClient()
                .id(2L)
                .name("Test")
                .age(22)
                .contact("(xx) xxxxx-xxxx")
                .email("ex@email.com")
                .gender('F')
                .build();

        when(clientService.insert(any())).thenReturn(client);

        mockMvc.perform(post(URL_CLIENT)
                        .content(objectMapper.writeValueAsString(client))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(clientService).insert(any(Client.class));
    }

    @Test
    void whenUpdateThenReturnSuccess() throws Exception{

        Client client = createClient()
                .id(2L)
                .name("Test")
                .age(22)
                .contact("(xx) xxxxx-xxxx")
                .email("ex@email.com")
                .gender('F')
                .build();

        when(clientService.findById(2L)).thenReturn(client);
        when(clientService.insert(any(Client.class))).thenReturn(client);

        mockMvc.perform(put(URL_CLIENT + "/{id}", 2L)
                        .content(objectMapper.writeValueAsString(client))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(clientService).update(any(ClientDTO.class), eq(2L));
    }
    @Test
    void deleteWithSuccess() throws Exception{

        mockMvc.perform(delete(URL_CLIENT + "/{id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(clientService).delete(anyLong());
    }

    private void startClient(){

        Client client = new Client(2L, "Test", 22, "(xx) xxxxx-xxxx", "ex@email.com", 'F');

        ClientDTO clientDTO = new ClientDTO(2L, "Test", 22, "(xx) xxxxx-xxxx", "ex@email.com", 'F');

    }
}