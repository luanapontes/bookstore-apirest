package com.bookstore.apirest.bookstoreapi.controllers;

import com.bookstore.apirest.bookstoreapi.dtos.BookDTO;
import com.bookstore.apirest.bookstoreapi.dtos.ClientDTO;
import com.bookstore.apirest.bookstoreapi.models.Client;
import com.bookstore.apirest.bookstoreapi.repositories.ClientRepository;
import com.bookstore.apirest.bookstoreapi.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/clients")
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public List<ClientDTO> listAll(){
        return ClientDTO.fromAll(clientService.listAll());
    }

    @GetMapping(value = "{id}")
    public ClientDTO findById(@PathVariable(value="id") Long id){
        return ClientDTO.from(clientService.findById(id));
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public void insert(@RequestBody ClientDTO clientDTO) {
        clientService.insert(Client.to(clientDTO));
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PutMapping(value = "/{id}")
    public void update(@RequestBody ClientDTO clientDTO, @PathVariable Long id){
        clientService.update(clientDTO, id);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id){
        clientService.delete(id);
    }
}
