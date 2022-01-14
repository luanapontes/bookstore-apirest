package com.bookstore.apirest.bookstoreapi.controllers;

import com.bookstore.apirest.bookstoreapi.dtos.BuyBookDTO;
import com.bookstore.apirest.bookstoreapi.models.BuyBook;
import com.bookstore.apirest.bookstoreapi.repositories.BuyBookRepository;
import com.bookstore.apirest.bookstoreapi.services.BuyBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/buybooks")
public class BuyBookController {

    private final BuyBookRepository buyBookRepository;

    private final BuyBookService buyBookService;

    @GetMapping
    public List<BuyBookDTO> listAll(){
        return BuyBookDTO.fromAll(buyBookService.listAll());
    }

    @GetMapping(value = "/{id}")
    public BuyBookDTO findById(@PathVariable(value="id") Long id){
        return BuyBookDTO.from(buyBookService.findById(id));
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public void insert(@RequestBody BuyBookDTO buyBookDTO) {
        buyBookService.insert(BuyBook.to(buyBookDTO));
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PutMapping(value = "/{id}")
    public void update(@RequestBody BuyBookDTO buyBookDTO, @PathVariable Long id){
        buyBookService.update(buyBookDTO, id);
    }
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id){
        buyBookService.delete(id);
    }
}
