package com.bookstore.apirest.bookstoreapi.test.service;

import com.bookstore.apirest.bookstoreapi.dtos.ClientDTO;
import com.bookstore.apirest.bookstoreapi.models.Client;
import com.bookstore.apirest.bookstoreapi.repositories.ClientRepository;
import com.bookstore.apirest.bookstoreapi.services.ClientService;
import com.bookstore.apirest.bookstoreapi.services.exceptions.ObjectNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.bookstore.apirest.bookstoreapi.test.builder.ClientBuilder.createClient;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Autowired
    private Client client;

    @Autowired
    private ClientDTO clientDTO;

    @Autowired
    private Optional<Client> optionalClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startClient();
    }

    @Test
    void whenFindAllThenReturnAnListOfClients() {

        when(clientRepository.findAll()).thenReturn(Stream.of(createClient().build()).collect(Collectors.toList()));

        List<Client> response = clientService.listAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(Client.class, response.get(0).getClass());

        assertEquals(2L, response.get(0).getId());
        Assertions.assertEquals("Test", response.get(0).getName());
        Assertions.assertEquals(22, response.get(0).getAge());
        Assertions.assertEquals("(xx) xxxxx-xxxx", response.get(0).getContact());
        Assertions.assertEquals("ex@email.com", response.get(0).getEmail());
        Assertions.assertEquals('F', response.get(0).getGender());

    }

    @Test
    void whenfindByIdTheReturnAnClientInstance() {

        when(clientRepository.findById(Mockito.anyLong())).thenReturn(optionalClient);

        Client response = clientService.findById(2L);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Client.class, response.getClass());
        Assertions.assertEquals(2L, response.getId());
        Assertions.assertEquals("Test", response.getName());
        Assertions.assertEquals(22, response.getAge());
        Assertions.assertEquals("(xx) xxxxx-xxxx", response.getContact());
        Assertions.assertEquals("ex@email.com", response.getEmail());
        Assertions.assertEquals('F', response.getGender());

    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException(){

        when(clientRepository.findById(anyLong())).thenThrow(new ObjectNotFoundException("Object not found"));

        try {
            clientService.findById(2L);
        } catch (Exception exception) {
            assertEquals(ObjectNotFoundException.class, exception.getClass());
            assertEquals("Object not found", exception.getMessage());
        }
    }

    @Test
    void whenInsertThenReturnSuccess() {

        Client client = createClient().build();
        clientService.insert(client);

        when(clientRepository.save(Mockito.any())).thenReturn(client);

        ArgumentCaptor<Client> clientArgumentCaptor = ArgumentCaptor.forClass(Client.class);
        verify(clientRepository).save(clientArgumentCaptor.capture());

        Client response = clientArgumentCaptor.getValue();

        assertNotNull(response);
        assertEquals(Client.class, response.getClass());
        Assertions.assertEquals(2L, response.getId());
        Assertions.assertEquals("Test", response.getName());
        Assertions.assertEquals(22, response.getAge());
        Assertions.assertEquals("(xx) xxxxx-xxxx", response.getContact());
        Assertions.assertEquals("ex@email.com", response.getEmail());
        Assertions.assertEquals('F', response.getGender());

    }

    @Test
    void whenUpdateThenReturnSuccess() {

        Client putClientRequest = createClient()
                .id(2L)
                .name("Test")
                .age(22)
                .contact("(xx) xxxxx-xxxx")
                .email("ex@email.com")
                .gender('F')
                .build();

        when(clientRepository.findById(anyLong())).thenReturn(optionalClient);

        clientService.update(ClientDTO.from(putClientRequest), 2L);

        ArgumentCaptor<Client> clientArgumentCaptor = ArgumentCaptor.forClass(Client.class);
        verify(clientRepository).save(clientArgumentCaptor.capture());

        Client response = clientArgumentCaptor.getValue();

        assertNotNull(response);
        assertEquals(Client.class, response.getClass());
        Assertions.assertEquals(2L, response.getId());
        Assertions.assertEquals("Test", response.getName());
        Assertions.assertEquals(22, response.getAge());
        Assertions.assertEquals("(xx) xxxxx-xxxx", response.getContact());
        Assertions.assertEquals("ex@email.com", response.getEmail());
        Assertions.assertEquals('F', response.getGender());

    }

    @Test
    void whenUpdateThenReturnAnObjectNotFoundException(){

        when(clientRepository.findById(anyLong())).thenThrow(new ObjectNotFoundException("Object not found"));

        try {
            clientService.findById(2L);
        } catch (Exception exception) {
            assertEquals(ObjectNotFoundException.class, exception.getClass());
            assertEquals("Object not found", exception.getMessage());
        }
    }

    @Test
    void deleteWithSuccess() {

        when(clientRepository.existsById(anyLong())).thenReturn(true);
        clientService.delete(2L);
        verify(clientRepository).existsById(anyLong());
        verify(clientRepository).deleteById(anyLong());
    }

    @Test
    void deleteWithObjectNotFoundException(){

        when(clientRepository.findById(anyLong())).thenThrow(new ObjectNotFoundException("Object not found"));
        try{
            clientService.delete(2L);
        } catch (Exception exception){
            assertEquals(ObjectNotFoundException.class, exception.getClass());
            assertEquals("Object not found", exception.getMessage());
        }
    }

    private void startClient(){

        Client client = new Client(2L, "Test", 22, "(xx) xxxxx-xxxx", "ex@email.com", 'F');

        ClientDTO clientDTO = new ClientDTO(2L, "Test", 22, "(xx) xxxxx-xxxx", "ex@email.com", 'F');

        optionalClient = Optional.of(new Client(2L, "Test", 22, "(xx) xxxxx-xxxx", "ex@email.com", 'F'));

    }
}