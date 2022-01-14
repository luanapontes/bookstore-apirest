package com.bookstore.apirest.bookstoreapi.services;

import com.bookstore.apirest.bookstoreapi.dtos.BookDTO;
import com.bookstore.apirest.bookstoreapi.services.exceptions.ObjectNotFoundException;
import com.bookstore.apirest.bookstoreapi.models.Book;
import com.bookstore.apirest.bookstoreapi.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> listAll(){
        return bookRepository.findAll();
    }

    public Book findById(Long id){
        Optional<Book> book = bookRepository.findById(id);
         return book.orElseThrow(
                 () -> new ObjectNotFoundException("Object not found"));

    }

    public Book insert(Book obj){
        return bookRepository.save(obj);
    }

    public void update(BookDTO bookDTO, Long id){
        Book bookSaved = bookRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("Object not found"));

        bookSaved.setAuthor(bookDTO.getAuthor());
        bookSaved.setCategories(bookDTO.getCategories());
        bookSaved.setIsbn(bookDTO.getIsbn());
        bookSaved.setQuantityAvailable(bookDTO.getQuantityAvailable());
        bookSaved.setSellPrice(bookDTO.getSellPrice());
        bookSaved.setResume(bookDTO.getResume());
        bookSaved.setName(bookDTO.getName());
        bookSaved.setYearOfPublication(bookDTO.getYearOfPublication());

        bookRepository.save(bookSaved);

    }

    public void delete(Long id){
        if(!bookRepository.existsById(id)){
           throw new ObjectNotFoundException("Object not found");
        }

        bookRepository.deleteById(id);
    }

    public List<Book> findAllBooksByCategoryName(String category) {
        return bookRepository.findBookByCategoriesName(category);
    }
}
