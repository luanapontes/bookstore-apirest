package com.bookstore.apirest.bookstoreapi.controllers;

import com.bookstore.apirest.bookstoreapi.dtos.BookDTO;
import com.bookstore.apirest.bookstoreapi.models.Book;
import com.bookstore.apirest.bookstoreapi.repositories.BookRepository;
import com.bookstore.apirest.bookstoreapi.services.BookService;
import com.bookstore.apirest.bookstoreapi.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/books")
public class BookController {

    private final BookService bookService;

    private final BookRepository bookRepository;

    private final CategoryService categoryService;

    @GetMapping
    public List<BookDTO> listAll(){
        return BookDTO.fromAll(bookService.listAll());
    }

    @GetMapping(value = "{id}")
    public BookDTO findById(@PathVariable(value="id") Long id){
        return BookDTO.from(bookService.findById(id));
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public void insert(@RequestBody BookDTO bookDTO) {
        bookService.insert(Book.to(bookDTO));
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PutMapping(value = "/{id}")
    public void update(@RequestBody BookDTO bookDTO, @PathVariable Long id){
        bookService.update(bookDTO, id);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id){
        bookService.delete(id);
    }

    @GetMapping(path = "/categoryname/{name}")
    public List<BookDTO> findAllBooksByCategoryName(@PathVariable String categoryName){
        return BookDTO.fromAll(bookRepository.findBookByCategoriesName(categoryName));
    }

}
